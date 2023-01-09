package com.telran.bankappfirsttry.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountRequestDTO {
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String email;
    private Instant creationDate;
    private Float balance;
}
