package com.telran.bankappfirsttry.dto;

import com.telran.bankappfirsttry.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountResponseDTO {
    private Long userId;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String email;
    private Instant creationDate;
    private Float balance;
    private Set<Transaction> transactions;

}
