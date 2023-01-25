package com.telran.bankappfirsttry.dto.converter;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.dto.TransactionRequestDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;

import java.time.Instant;

public class ConverterDTO {

    public static Account convertDtoToAccount (AccountRequestDTO request){
        return Account.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .country(request.getCountry())
            .city(request.getCity())
            .email(request.getEmail())
            .creationDate(Instant.now())
            .balance(request.getBalance())
               .transactions(request.getTransactions())//make null?? since we create an account without a transaction
            .build();
    }
    public static AccountResponseDTO convertAccountToDTO(Account account){
        return AccountResponseDTO.builder()
                .id(account.getUserId())
                .firstName(account.getFirstName())
                .lastName(account.getLastName())
                .email(account.getEmail())
                .city(account.getCity())
                .country(account.getCountry())
                .creationDate(account.getCreationDate())
                .balance(account.getBalance())
                .transactions(account.getTransactions())
                .build();

    }
    public static Transaction convertDtoToTransaction(TransactionRequestDTO request, Long userId, Float amount){
        return Transaction.builder()
                .accountTo(userId)
                .accountFrom(userId)
                .dateTime(Instant.now())
                .amount(amount)
                .build();

    }

}
