package com.telran.bankappfirsttry.dto;

import com.telran.bankappfirsttry.entity.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionRequestDTO {
    private Long accountTo;
    private Long accountFrom;
    private Instant dateTime;
    private Float amount;
    private TransactionType type;


}
