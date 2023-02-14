package com.telran.bankappfirsttry.serviceImpl;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.mapper.AccountMapper;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    AccountRepository accountRepository;
    @Mock
    TransactionRepository transactionRepository;
    @Mock
    AccountMapper mapper;
    @InjectMocks
    AccountServiceImpl service;
    @Test
    @DisplayName("should save() account")
    public void shouldSaveAccount(){
        AccountRequestDTO request = AccountRequestDTO.builder()
                .firstName("Jane")
                .lastName("Atkins")
                .country("Germany")
                .city("Berlin")
                .email("ja@gmail.com")
                .creationDate(Instant.now())
                .balance(100F)
                .build();
        Account account = Account.builder()
                        .userId(1L)
                        .firstName(request.getFirstName())
                        .lastName(request.getLastName())
                        .country(request.getCountry())
                        .city(request.getCity())
                        .email(request.getEmail())
                        .creationDate(request.getCreationDate())
                        .balance(request.getBalance())
                .build();

        Mockito
                .when(accountRepository.findById(account.getUserId()))
                       .thenReturn(Optional.of(account));

        Mockito
                .when(accountRepository.save(ArgumentMatchers.argThat(
                        savedAccount ->{
                            return savedAccount.equals(request.getFirstName())
                                    && savedAccount.getLastName().equals(request.getLastName())
                                    && savedAccount.getCountry().equals(request.getCountry())
                                    && savedAccount.getCity().equals(request.getCity())
                                    && savedAccount.getEmail().equals(request.getEmail())
                                    && savedAccount.getCreationDate().equals(request.getCreationDate())
                                    && savedAccount.getBalance().equals(request.getBalance());
                        }
                )))
                .thenReturn(account);


        service.createAccount(request);
    }



    @Test
    @DisplayName("should throw 404 NOT_FOUND when no such account")
    public void shouldThrow404WhenNoSuchAccount(){

        Long accountId = 1L;
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = "account is not found";

        Mockito
                .when(accountRepository.findById(accountId))
                .thenReturn(Optional.empty());
        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,
                ()->service.getAccountById(accountId)
        );
        Assertions.assertEquals(expectedStatus, exception.getStatus());
        Assertions.assertEquals(expectedMessage, exception.getReason());


    }


}
