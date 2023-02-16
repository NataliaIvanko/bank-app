package com.telran.bankappfirsttry.serviceImpl;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.mapper.AccountMapper;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
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

    @Nested
    @DisplayName("createAccount()")
    class createAccount {
        @Test
        @DisplayName("should throw 404 NOT_FOUND when no such account")
        public void shouldThrow404WhenNoSuchAccount() {

            Long accountId = 1L;
            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = "account is not found";

            Mockito
                    .when(accountRepository.findById(accountId))
                    .thenReturn(Optional.empty());
            ResponseStatusException exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    () -> service.getAccountById(accountId)
            );
            Assertions.assertEquals(expectedStatus, exception.getStatus());
            Assertions.assertEquals(expectedMessage, exception.getReason());


        }

        @Test
        @DisplayName("should save() account")
        public void shouldSaveAccount() {
            AccountRequestDTO request = AccountRequestDTO.builder() //expected
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
                    .when(accountRepository.save(ArgumentMatchers.argThat(
                            savedAccount -> {
                                return savedAccount.getUserId().equals(account.getUserId())
                                        && savedAccount.getFirstName().equals(account.getFirstName())
                                        && savedAccount.getLastName().equals(account.getLastName())
                                        && savedAccount.getCountry().equals(account.getCountry())
                                        && savedAccount.getCity().equals(account.getCity())
                                        && savedAccount.getEmail().equals(account.getEmail())
                                        && savedAccount.getCreationDate().equals(account.getCreationDate())
                                        && savedAccount.getBalance().equals(account.getBalance());
                            }
                    )))
                   .thenReturn(account);
//            Mockito
//                    .when(accountRepository.save(ArgumentMatchers.any()))
//                            .thenReturn(account);
            Mockito
                    .when(mapper.dtoToAccount(request))
                    .thenReturn(account);
            service.createAccount(request);
            Mockito.verify(accountRepository).save(account);
        }
    }

    @Nested
    @DisplayName("deleteAccountById()")
    class deleteAccountById {
        @Test
        @DisplayName("should call deleteAccountById() ")
        public void shouldCallDeleteAccountById() {
            Long requestedId = 1L;
            Mockito.doNothing()
                    .when(accountRepository)
                    .deleteById(requestedId);
            service.deleteAccountByUserId(requestedId);

            Mockito.verify(accountRepository, Mockito.times(1))
                    .deleteById(requestedId);


        }
    }
}

//            Mockito
//                    .when(accountRepository.findById(account.getUserId()))
//                    .thenReturn(Optional.of(account));

//            Mockito - оба проходят тест
//                    .verify(accountRepository, Mockito.times(1))
//                    .save(ArgumentMatchers.argThat((passedIn)-> {
//                        return passedIn.equals(account);
//                    }));

