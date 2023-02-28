package com.telran.bankappfirsttry.service.interfaces;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;

import java.time.Instant;
import java.util.List;

public interface AccountService {
    void createAccount(AccountRequestDTO request);

    List<AccountResponseDTO> getAccountsFiltered(List<String> city, Instant creationDate, String sort);

    AccountResponseDTO getAccountById(Long userId);

    void updateBalance(Long id, Float amount, AccountRequestDTO requestDTO);

    void updateAccountById(Long userId, AccountRequestDTO account);

    void transferMoneyBetweenAccounts(Long idTo, Long idFrom, Float amount, AccountRequestDTO account, Long id);

    void deleteAccountByUserId(Long userId);
}
