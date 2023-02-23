package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.enums.TransactionType;
import com.telran.bankappfirsttry.mapper.AccountMapper;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

//@RequiredArgsConstructor
@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private final AccountMapper accountMapper;


    @Override
    public void createAccount(AccountRequestDTO request) {
        Account account = accountMapper.dtoToAccount(request);
        accountRepository.save(account);
    }

    @Override
    public AccountResponseDTO getAccountById(Long userId) {
        var account = accountRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(NOT_FOUND, "account is not found"));

        return accountMapper.accountToDto(account);
    }

    @Transactional
    @Override
    public void updateAccountById(Long userId, AccountRequestDTO account) {
        var newInfoAcc = accountRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User with id" + userId + "not found"));

        if (account.getFirstName() != null) {
            newInfoAcc.setFirstName(account.getFirstName());
        }
        if (account.getLastName() != null) {
            newInfoAcc.setLastName(account.getLastName());
        }
        if (account.getCountry() != null) {
            newInfoAcc.setCountry(account.getCountry());
        }
        if (account.getCity() != null) {
            newInfoAcc.setCity(account.getCity());
        }
        if (account.getEmail() != null) {
            newInfoAcc.setEmail(account.getEmail());
        }
      accountRepository.save(newInfoAcc);
    }
    @Override
    public void updateBalance(Long id, Float amount, AccountRequestDTO requestDTO){
       var newInfoAccount = findAccountById(id);
        if(requestDTO.getBalance() != null){
            newInfoAccount.setBalance(requestDTO.getBalance()+amount);
            var transaction = createTransaction(id, id, amount);
            newInfoAccount.getTransactions().add(transaction);
            accountRepository.save(newInfoAccount);
        }
    }

    @Transactional
    @Override
    public void transferMoneyBetweenAccounts(Long idTo, Long idFrom, Float amount, AccountRequestDTO account, Long id) {
        Account accountTo = findAccountById(idTo);
        Account accountFrom = findAccountById(idFrom);

        if (!isEnoughMoney(amount, idFrom)) {
            throw new ResponseStatusException(BAD_REQUEST, "source account balance is lower than the transferred amount");
        }
        accountTo.setBalance(account.getBalance() + amount);
        accountFrom.setBalance(account.getBalance() - amount);

        var transaction = createTransaction(idTo, idFrom, amount);
        accountFrom.getTransactions().add(transaction);
        accountRepository.save(accountFrom);
        accountTo.getTransactions().add(transaction);
        accountRepository.save(accountTo);
    }

    private Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException
                        (NOT_FOUND, "account " + id + " is not found"));
    }

    private boolean isEnoughMoney(Float amount, Long idFrom) {
        var amountCheck = findAccountById(idFrom);
        return amountCheck.getBalance() >= amount;
    }

    private Transaction createTransaction(Long idTo, Long idFrom, Float amount) {
        var transaction = Transaction.builder()
                .accountTo(idTo)
                .accountFrom(idFrom)
                .dateTime(Instant.now())
                .amount(amount)
                .build();

        if (transaction.getAccountTo().equals(transaction.getAccountFrom()) && amount > 0) {
            transaction.setType(TransactionType.DEPOSIT);
        }
        if (transaction.getAccountTo().equals(transaction.getAccountFrom()) && amount < 0) {
            transaction.setType(TransactionType.WITHDRAW);
        }
        if (!transaction.getAccountTo().equals(transaction.getAccountFrom())) {
            transaction.setType(TransactionType.TRANSFER);
        }
        transactionRepository.save(transaction);

        return transaction;
    }

    @Override
    public List<AccountResponseDTO> getAccountsFiltered(List<String> city, Instant creationDate, String sort) { //refactor

        if (city != null && creationDate == null) { //city
            if (sort == null) {
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .map(accountMapper::accountToDto)
                        .toList();
            }
            if (sort.equalsIgnoreCase("creationDate")) { //city and crDate
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .map(accountMapper::accountToDto)
                        .toList();
            }
            if (sort.equalsIgnoreCase("-creationDate")) {
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .map(accountMapper::accountToDto)
                        .toList();
            }
        }
        if (city == null && creationDate != null) {
            if (sort == null) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .map(accountMapper::accountToDto)
                        .toList();
            }
            if (sort.equalsIgnoreCase("creationDate")) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .map(accountMapper::accountToDto)
                        .toList();
            }
            if (sort.equalsIgnoreCase("-creationDate")) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .map(accountMapper::accountToDto)
                        .toList();
            }
        }
        if (city != null)
            if (creationDate != null) {
                if (sort == null) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .map(accountMapper::accountToDto)
                            .toList();
                }
                if (sort.equalsIgnoreCase("creationDate")) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .sorted(Comparator.comparing(Account::getCreationDate))
                            .map(accountMapper::accountToDto)
                            .toList();
                }
                if (sort.equalsIgnoreCase("-creationDate")) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                            .map(accountMapper::accountToDto)
                            .toList();
                }
            }

        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getUserId))
                .map(accountMapper::accountToDto)
                .toList();
    }
    @Override
    public void deleteAccountByUserId(Long userId) {
        accountRepository.deleteById(userId);
    }
}



