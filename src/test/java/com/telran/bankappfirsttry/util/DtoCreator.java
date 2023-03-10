package com.telran.bankappfirsttry.util;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.dto.TransactionRequestDTO;
import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.entity.enums.TransactionType;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

public class DtoCreator {

    public static AccountRequestDTO getAccountRequestDto() {
        return AccountRequestDTO.builder()
                .firstName("Jane")
                .lastName("Atkins")
                .country("Germany")
                .city("Berlin")
                .email("ja@gmail.com")
                .creationDate(Instant.now())
                .balance(100F)
                .transactions(new HashSet<>(Set.of()))

                .build();
    }

    public static AccountResponseDTO getAccountResponseDto() {
        return AccountResponseDTO.builder()
                .userId(1L)
                .firstName("Jane")
                .lastName("Atkins")
                .country("Germany")
                .city("Berlin")
                .email("ja@gmail.com")
                .creationDate(Instant.now())
                .balance(100F)
                .build();
    }

    public static TransactionRequestDTO getTransactionRequestDto() {
        return TransactionRequestDTO.builder()
                .accountFrom(1L)
                .accountTo(2L)
                .amount(500F)
                .type(TransactionType.TRANSFER)
                .dateTime(Instant.now())
                .build();
    }


    public static TransactionResponseDTO getTransactionResponseDto(){
        return TransactionResponseDTO.builder()
                .id(1L)
                .dateTime(Instant.now())
                .type(EntityCreator.getTransaction().getType())
                .amount(500F)
                .accountFrom(1L)
                .accountTo(2L)
                .build();
    }
}
