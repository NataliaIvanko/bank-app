package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.service.interfaces.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@AllArgsConstructor
@RestController
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/accounts")
    public void createAccount(@RequestBody @Valid AccountRequestDTO requestDTO) { //check if data valid.
        accountService.createAccount(requestDTO);
    }

    @GetMapping("/accounts/{userId}")
    public AccountResponseDTO findAccountById(@PathVariable("userId") Long userId) {
        return accountService.getAccountById(userId);
    }

    @GetMapping("/accounts")
    public List<AccountResponseDTO> getAccountsFiltered(@RequestParam(value = "city", required = false) List<String> city,
                                                        @RequestParam(value = "date", required = false) Instant creationDate,
                                                        @RequestParam(value = "sort", required = false) String sort) {
        return accountService.getAccountsFiltered(city, creationDate, sort);
    }

    @PatchMapping("/accounts/{userId}")
    public void updateAccountById(@PathVariable("userId") Long userId,
                                  @RequestParam(value = "amount", required = false) Float amount,
                                  @RequestBody AccountRequestDTO account) {
        accountService.updateAccountById(userId, amount, account);
    }
    @PatchMapping("/accounts/balance/{userId}")
            public void updateBalance(@PathVariable("userId") Long id,
                                      @RequestParam(value = "amount") Float amount,
                                     @RequestBody AccountRequestDTO requestDTO){
        accountService.updateBalance(id, amount,requestDTO);
    }

    @PutMapping("/accounts")
    public void transferMoneyBetweenAccounts(@RequestParam(value = "idFrom", required = true) Long idFrom,
                                             @RequestParam(value = "idTo", required = true) Long idTo,
                                             @RequestParam(value = "amount", required = true) Float amount,
                                             @RequestParam(value = "id", required = false) Long id,
                                             @RequestBody AccountRequestDTO account) {
        accountService.transferMoneyBetweenAccounts(idFrom, idTo, amount, account, id);
    }

    @DeleteMapping("/accounts/{userId}")
    public void deleteAccountByUserId(@PathVariable("userId") Long userId) {
        accountService.deleteAccountByUserId(userId);
    }




}
