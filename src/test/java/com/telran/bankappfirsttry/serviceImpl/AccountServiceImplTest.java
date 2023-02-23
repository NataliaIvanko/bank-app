package com.telran.bankappfirsttry.serviceImpl;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.mapper.AccountMapper;
import com.telran.bankappfirsttry.repository.AccountRepository;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.impl.AccountServiceImpl;
import com.telran.bankappfirsttry.util.DtoCreator;
import com.telran.bankappfirsttry.util.EntityCreator;
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
        @DisplayName("should save() account")
        public void shouldSaveAccount() {
            AccountRequestDTO request = DtoCreator.getAccountRequestDto();
            Account account = EntityCreator.getAccount();
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
    @DisplayName("getAccountById")
    class getAccountById {
        @Test
        @DisplayName("should throw 404 NOT_FOUND when no such account")
        public void shouldThrow404WhenNoSuchAccount() {
            Long accountId = 1L;
            Account account = EntityCreator.getAccount();
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
            Mockito.verify(accountRepository, Mockito.times(0)).save(account);
        }
        @Test
        @DisplayName("should return dto when there is such an account")
        public void shouldReturnDtoWhenThereIsSuchAnAccount(){
            Long requestedId = 1L;
            Account account = EntityCreator.getAccount();
            AccountResponseDTO expectedResponse = DtoCreator.getAccountResponseDto();

            Mockito
                    .when(accountRepository.findById(requestedId))
                    .thenReturn(Optional.of(account));
            Mockito
                    .when(mapper.accountToDto(account))
                    .thenReturn(expectedResponse);
            AccountResponseDTO actualResponseDto = service.getAccountById(requestedId);
            Assertions.assertEquals(expectedResponse, actualResponseDto);
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
    @Nested
    @DisplayName("updateAccountById()")
    class updateAccountById{
        @Test
        @DisplayName("should throw 404 not found exception when there is no such an account")
        void ShouldThrowNotFoundWhenAccountDoesNotExist(){
            Long requestedId = 1L;
            Float amount = 500F;
            AccountRequestDTO account = DtoCreator.getAccountRequestDto();
            HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
            String expectedMessage = "User with id" + requestedId + "not found";

            Mockito
                    .when(accountRepository.findById(requestedId))
                    .thenReturn(Optional.empty());
            ResponseStatusException exception = Assertions.assertThrows(
                    ResponseStatusException.class,
                    ()-> service.updateAccountById(requestedId, account)
            );
            Assertions.assertEquals(expectedStatus,exception.getStatus());
            Assertions.assertEquals(expectedMessage,exception.getReason());
        }
        @Test
        @DisplayName("")
        void shouldReturnPatchedAccount(){
            Long requestedId = 1L;
            AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
            Account account = EntityCreator.getAccount();
            Account updatedAccount = Account.builder()
                    .userId(account.getUserId())
                    .firstName("newFirstName")
                    .lastName("newLastName")
                    .country("newCountry")
                    .city("newCity")
                    .email("newEmail")
                    .balance(account.getBalance())
                    .creationDate(account.getCreationDate())
                    .build();

            Mockito
                    .when(accountRepository.findById(requestedId))
                    .thenReturn(Optional.of(account));
           Mockito
                   .when(accountRepository.save(ArgumentMatchers.argThat((passedIn)-> {
                        return passedIn.equals(account);
                    }))).thenReturn(updatedAccount);

            service.updateAccountById(requestedId,requestDTO);
            Mockito.verify(accountRepository, Mockito.times(1)).save(account);



        }
    }
}
