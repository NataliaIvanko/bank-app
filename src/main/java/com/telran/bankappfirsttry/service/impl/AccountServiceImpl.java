package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.TransactionType;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    //  private final static AtomicInteger userId = new AtomicInteger();
  //  private final Map<Long, Account> accountsMap = new HashMap<Long, Account>();

//    private final List<Transaction> transactionList = new ArrayList<>();

    @Override
    public void createAccount(AccountRequestDTO request) {
        var dbAccount = Account.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .country(request.getCountry())
                .city(request.getCity())
                .email(request.getEmail())
                .creationDate(Instant.now())
                .balance(request.getBalance())
                //    .transactions(account.getTransactions())
                .build();

        accountRepository.save(dbAccount);

    }

    @Override
    public ResponseEntity<Account> getAccountById(Long userId) {
        var account = accountRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "account is not found"));

        return ResponseEntity.ok(account);
    }

    @Override
    public Account updateAccountById(Long userId, Account account, Float amount) {
        var newInfoAcc = accountRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND));

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
        if (account.getBalance() != null) {

            newInfoAcc.setBalance(account.getBalance() + amount);
            // Transaction transaction = new Transaction();
            var transaction = Transaction.builder()
                    .accountTo(userId)
                    .accountFrom(userId)
                    .dateTime(Instant.now())
                    .amount(amount)
                    .build();

            transactionRepository.save(transaction);

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
            newInfoAcc.getTransactions().add(transaction);
            accountRepository.save(newInfoAcc);
        }
        accountRepository.save(newInfoAcc);
        return newInfoAcc;
    }

    @Override
    public List<Account> getAccountsFiltered(List<String> city, Instant creationDate, String sort) {//date, city, country, sorted by date asc, desc

        if (city != null && creationDate == null) { //city
            if (sort == null) {
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .toList();
            }
            if (sort.equalsIgnoreCase("creationDate")) { //city and crDate
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equalsIgnoreCase("-creationDate")) {
                return accountRepository.findAllByCityInIgnoreCase(city).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .toList();
            }
        }
        if (city == null && creationDate != null) {
            if (sort == null) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .toList();
            }
            if (sort.equalsIgnoreCase("creationDate")) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equalsIgnoreCase("-creationDate")) {
                return accountRepository.findAccountByCreationDate(creationDate).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                        .toList();
            }
        }
        if (city != null)
            if (creationDate != null) {
                if (sort == null) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .toList();
                }
                if (sort.equalsIgnoreCase("creationDate")) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .sorted(Comparator.comparing(Account::getCreationDate))
                            .toList();
                }
                if (sort.equalsIgnoreCase("-creationDate")) {
                    return accountRepository.findAccountByCityInAndCreationDate(city, creationDate).stream()
                            .sorted(Comparator.comparing(Account::getCreationDate, Comparator.reverseOrder()))
                            .toList();
                }
            }
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getUserId))
                .toList();
    }

    @Override
    public void transferMoneyBetweenAccounts(Long idTo, Long idFrom, Float amount, Account account, Long id) {
        Account accountTo = accountRepository.findById(idTo).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        Account accountFrom = accountRepository.findById(idFrom).orElseThrow(() -> new ResponseStatusException(NOT_FOUND));
        if (!isAccountPresent(idTo)) {
            throw new ResponseStatusException(NOT_FOUND, "destination account is not found");
        }
        if (!isAccountPresent(idFrom)) {
            throw new ResponseStatusException(NOT_FOUND, "source account is not found");
        }
        if (!isEnoughMoney(amount, idFrom)) {
            throw new ResponseStatusException(BAD_REQUEST, "source account balance" + idFrom+ "is lower than the transferred amount");
        }

        accountTo.setBalance(account.getBalance() + amount);
        accountFrom.setBalance(account.getBalance() - amount);

        var transaction = Transaction.builder()
                .accountTo(idTo)
                .accountFrom(idFrom)
                .dateTime(Instant.now())
                .type(TransactionType.TRANSFER)
                .amount(amount)
                .build();
        System.out.println(transaction);
        transactionRepository.save(transaction);
        accountFrom.getTransactions().add(transaction);
        accountRepository.save(accountFrom);
        accountTo.getTransactions().add(transaction);
        accountRepository.save(accountTo);
    }
    private boolean isAccountPresent(Long id) {
        return accountRepository.findById(id).stream()
                .anyMatch(acc -> acc.getUserId().equals(id));
    }

    private boolean isEnoughMoney(Float amount, Long idFrom) {
        var amountCheck = accountRepository.findById(idFrom).orElseThrow(() -> new ResponseStatusException(HttpStatus.I_AM_A_TEAPOT));
        return amountCheck.getBalance() >= amount;
    }

    @Override
    public void deleteAccountByUserId(Long userId) {
        accountRepository.deleteById(userId);
    }

}



