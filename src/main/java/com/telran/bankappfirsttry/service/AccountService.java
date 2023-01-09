package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.entity.Account;
import org.springframework.http.ResponseEntity;

import java.time.Instant;
import java.util.List;

public interface AccountService {
    void createAccount(AccountRequestDTO request);
    List<Account> getAccountsFiltered(List<String> city, Instant creationDate, String sort);
    ResponseEntity<Account> getAccountById(Long userId);
    Account updateAccountById(Long userId, Account account, Float amount);
  //  Account updateAccountByAmount(Integer id, Float amount);
    void transferMoneyBetweenAccounts(Long idTo, Long idFrom, Float amount, Account account, Long id);

 //   List<Account> getAccountsByFilters(String city);
    void deleteAccountByUserId(Long userId);

  //  void transferMoneyBetweenAccounts(Integer fromId, Integer toId, Float amount, Account account);


//  Account findAccountById(Integer userId);
//  ResponseEntity<Account> findAccountById(Long userId);



}
