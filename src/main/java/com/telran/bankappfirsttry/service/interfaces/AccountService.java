package com.telran.bankappfirsttry.service.interfaces;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;

import java.time.Instant;
import java.util.List;

public interface AccountService {
    void createAccount(AccountRequestDTO request);
    List<AccountResponseDTO> getAccountsFiltered(List<String> city, Instant creationDate, String sort);
    AccountResponseDTO getAccountById(Long userId);
 //  void updateAccountById(Long userId, Float amount, /*TransactionRequestDTO request,*/ AccountRequestDTO account);
   void updateAccountById(Long userId, Float amount, /*TransactionRequestDTO request,*/ AccountRequestDTO account);
   void transferMoneyBetweenAccounts(Long idTo, Long idFrom, Float amount, AccountRequestDTO account, Long id);
    void deleteAccountByUserId(Long userId);





  //  void transferMoneyBetweenAccounts(Integer fromId, Integer toId, Float amount, Account account);
    //  List<Account> getAccountsFiltered(List<String> city, Instant creationDate, String sort);

//  Account findAccountById(Integer userId);
//  ResponseEntity<Account> findAccountById(Long userId);
//  ResponseEntity<Account> getAccountById(Long userId);
// Account updateAccountById(Long userId, Float amount, Account account);
}
