package com.telran.bankappfirsttry.util;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.enums.TransactionType;

import java.time.Instant;

public class EntityCreator {

    public static Account getAccount(){
        Account account = Account.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Atkins")
                .country("Germany")
                .city("Berlin")
                .email("ja@gmail.com")
               // .creationDate(Instant.parse("2022-02-15T13:18:47.00Z"))
                .creationDate(Instant.now())
                .balance(100F)
                .build();
        return account;
    }
    public static Transaction getTransaction(){
        Transaction transaction = Transaction.builder()
                .id(1L)
             //   .dateTime(Instant.parse("2022-02-15T13:18:47.00Z"))
                .dateTime(Instant.now())
                .type(TransactionType.DEPOSIT)
                .amount(500F)
                .accountFrom(1L)
                .accountTo(2L)
                .build();
        return transaction;
    }
}
