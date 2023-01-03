package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.TransactionType;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

  //  private final static AtomicInteger userId = new AtomicInteger();
    private final Map<Long, Account> accountsMap = new HashMap<Long, Account>();

    private final List<Transaction> transactionList = new ArrayList<>();

    List<String> cities = accountsMap.values().stream()
            .map(Account::getCity)
            .collect(Collectors.toList());


    @Override
    public void createAccount(Account account) {
       var dbAccount = Account.builder()
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .country(account.getCountry())
                .city(account.getCity())
                .email(account.getEmail())
                .creationDate(Instant.now())
                .balance(account.getBalance())
           //    .transactions(account.getTransactions())
                .build();

         accountRepository.save(dbAccount);

}

@Override
public ResponseEntity<Account> getAccountById(Long userId) {
    var  account = accountRepository.findById(userId).get();
    if (account == null) {
        throw new ResponseStatusException(NOT_FOUND, "account is not found");
    }
    return ResponseEntity.ok(account);
}
    @Override
    public Account updateAccountById(Long userId, Account account, Float amount) {
        var newInfoAcc = accountRepository.findById(userId)
                .orElseThrow(()-> new ResponseStatusException(NOT_FOUND));
        // if (userId == null) {
       //      throw new ResponseStatusException(NOT_FOUND);
     //    }
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
         //   newInfoAcc.setTransactions(List.of(transaction));
         //  transaction.setAccountTo(userId);
      //      transaction.setAccountFrom(userId);
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
            // transaction.setAmount(amount);
            //   accountsMap.get(userId).addTransactionToList(transaction.getId());
           // System.out.println(transaction);


        }
        accountRepository.save(newInfoAcc);

        return newInfoAcc;
    }

    @Override
    public List<Account> getAccountsFiltered(List<String> city, Instant creationDate, String sort) {//date, city, country, sorted by date asc, desc

        if (city != null && creationDate == null) { //city
            if (sort == null) {
                return accountRepository.findAllByCityIn(city).stream()
                        .toList();
            }
            if (sort.equalsIgnoreCase("creationDate")) { //city and crDate
                return accountRepository.findAllByCityIn(city).stream()
                        .sorted(Comparator.comparing(Account::getCreationDate))
                        .toList();
            }
            if (sort.equalsIgnoreCase("-creationDate")) {
               return  accountRepository.findAllByCityIn(city).stream()
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
        if (!isAccountPresent(idTo)) {
            throw new ResponseStatusException(NOT_FOUND, "destination account is not found");
        }
        if (!isAccountPresent(idFrom)) {
            throw new ResponseStatusException(NOT_FOUND, "source account is not found");
        }
        if (!isEnoughMoney(amount, id)) {
            throw new ResponseStatusException(BAD_REQUEST, "either the source or the destination account balance is lower than the transferred amount");
        }

        accountsMap.get(idTo).setBalance(accountsMap.get(idTo).getBalance() + amount);

        accountsMap.get(idFrom).setBalance(accountsMap.get(idFrom).getBalance() - amount);
        Transaction transaction = new Transaction();
        transaction.setAccountTo(idTo);
        transaction.setAccountFrom(idFrom);
        transaction.setType(TransactionType.TRANSFER);
        transaction.setAmount(amount);
        System.out.println(transaction);
  //      accountsMap.get(idTo).addTransactionToList(transaction.getId());
  //      accountsMap.get(idFrom).addTransactionToList(transaction.getId());


        //  return transaction;

    }

    private boolean isAccountPresent(Long id) {
        return accountRepository.findById(id).stream()
                .anyMatch(acc -> acc.getUserId().equals(id));


    }
    private boolean isEnoughMoney(Float amount, Long id) {
        var amountCheck = accountRepository.findById(id).orElseThrow();
        return  amountCheck.getBalance()>=amount;

    }



    @Override
    public void deleteAccountByUserId(Long userId) {
        accountRepository.deleteById(userId);

    }


}



