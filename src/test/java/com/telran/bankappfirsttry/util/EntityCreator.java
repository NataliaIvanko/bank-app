package com.telran.bankappfirsttry.util;

import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.enums.TransactionType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class EntityCreator {

    public static Account getAccount(){
        return Account.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Atkins")
                .country("Germany")
                .city("Berlin")
                .email("ja@gmail.com")
                .creationDate(DtoCreator.getAccountRequestDto().getCreationDate())
                .balance(100F)
                .transactions(new HashSet<>(Set.of()))
                .build();
    }
    public static Transaction getTransaction(){
        return  Transaction.builder()
                .id(1L)
                .dateTime(Instant.now())
                .type(TransactionType.DEPOSIT)
                .amount(500F)
                .accountFrom(1L)
                .accountTo(1L)
                .build();
    }
}
