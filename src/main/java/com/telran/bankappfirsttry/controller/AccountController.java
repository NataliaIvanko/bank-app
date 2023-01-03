package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;


@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;



    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }


    @PostMapping("/accounts")
    public void createAccount(@RequestBody Account account){
        accountService.createAccount(account);

    }


    @GetMapping("/accounts/{userId}")
    public ResponseEntity<Account> findAccountById(@PathVariable("userId") Long userId) {
        return  accountService.getAccountById(userId);
    }
    @GetMapping("/accounts")   //creationDate
    public List<Account> getAccountsFiltered(@RequestParam (value = "city", required = false) List<String>city,
                                             @RequestParam(value = "date", required = false) Instant creationDate,
                                             @RequestParam(value= "sort", required = false) String sort){
        return accountService.getAccountsFiltered(city, creationDate, sort);
    }

    @PatchMapping("/accounts/{userId}")
    public Account updateAccountById(@PathVariable("userId") Long userId,
                                     @RequestParam(value = "amount", required = false) Float amount,
                                     @RequestBody Account account){
        return accountService.updateAccountById(userId, account, amount);
    }


//accounts?from=<fromId>&to=<toId>&amount=<moneyAmount>:
    @PutMapping("/accounts")
    public void transferMoneyBetweenAccounts(@RequestParam(value = "idFrom", required = true ) Long idFrom,
                                             @RequestParam(value = "idTo", required = true) Long idTo,
                                             @RequestParam(value = "amount", required = true) Float amount,
                                             @RequestParam(value = "id", required = false) Long id,
                                             @RequestBody Account account){
        accountService.transferMoneyBetweenAccounts(idFrom, idTo, amount, account, id);

    }
    /*
    @DeleteMapping("/accounts/{userId}")
    public void deleteAccountByUserId(@PathVariable ("userId") Long userId){
        accountService.deleteAccountByUserId(userId);
    }

     */

}
