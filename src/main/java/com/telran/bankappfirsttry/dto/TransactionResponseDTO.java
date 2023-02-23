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
public class TransactionResponseDTO {

    private Long id;
    private Instant dateTime;
    private TransactionType type;
    private Float amount;
    private Long accountFrom;
    private Long accountTo;
}
