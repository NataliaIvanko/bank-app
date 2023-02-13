package com.telran.bankappfirsttry.dto;

import com.telran.bankappfirsttry.entity.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountRequestDTO {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    @NotBlank
    @Email (message = "Email format is invalid")
    @Length(min= 8, max = 50)
    private String email;

    private Instant creationDate;

    private Float balance;

    private Set<Transaction> transactions;
}
