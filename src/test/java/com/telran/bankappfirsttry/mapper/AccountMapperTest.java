package com.telran.bankappfirsttry.mapper;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.entity.Account;
import com.telran.bankappfirsttry.util.DtoCreator;
import com.telran.bankappfirsttry.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AccountMapperTest {

    @Spy
    private final AccountMapper accountMapper = new AccountMapperImpl();

    @Test
    @DisplayName("mapping account into accountDto")
    public void accountToAccountDto(){
        Account account = EntityCreator.getAccount();
        AccountResponseDTO expectedDto = DtoCreator.getAccountResponseDto();
        Assertions.assertEquals(expectedDto, accountMapper.accountToDto(account));
    }
/*
    @Test
    @DisplayName("mapping accountDto into account")
    public void accountDtoToAccount1(){
        AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
        Account expectedAccount = EntityCreator.getAccount();
        Assertions.assertEquals(expectedAccount, accountMapper.dtoToAccount(requestDTO));
    }
*/

    @Test
    @DisplayName("mapping accountDto into account")
    public void accountDtoToAccount(){
        AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
        Account expectedAccount = accountMapper.dtoToAccount(requestDTO);
        compareEntityToRequestDto(expectedAccount, requestDTO);
    }


    @Test
    @DisplayName("mapping accountDto into account")
    public void accountDtoToAccountPartially() {
        AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
        requestDTO.setFirstName("Jane");
        Account expectedAccount = EntityCreator.getAccount();
        Assertions.assertEquals(expectedAccount.getFirstName(), accountMapper.dtoToAccount(requestDTO).getFirstName());
    }

    @DisplayName("compare entity to Dto")
    private void compareEntityToRequestDto(Account account, AccountRequestDTO requestDTO){
        assertAll(
                ()-> assertEquals(account.getFirstName(), requestDTO.getFirstName()),
                ()-> assertEquals(account.getLastName(), requestDTO.getLastName()),
                ()-> assertEquals(account.getCountry(), requestDTO.getCountry()),
                ()-> assertEquals(account.getCity(), requestDTO.getCity()),
                ()-> assertEquals(account.getEmail(), requestDTO.getEmail()),
                ()-> assertEquals(account.getCreationDate(), requestDTO.getCreationDate()),
                ()-> assertEquals(account.getBalance(), requestDTO.getBalance()),
                ()-> assertEquals(account.getTransactions(), requestDTO.getTransactions())
        );

    }
    @Test
    @DisplayName("compare entity to Dto")
    private void compareEntityToResponseDto(Account account, AccountResponseDTO responseDTO){
        assertAll(
                ()-> assertEquals(account.getFirstName(), responseDTO.getFirstName()),
                ()-> assertEquals(account.getLastName(), responseDTO.getLastName()),
                ()-> assertEquals(account.getCountry(), responseDTO.getCountry()),
                ()-> assertEquals(account.getCity(), responseDTO.getCity()),
                ()-> assertEquals(account.getEmail(), responseDTO.getEmail()),
                ()-> assertEquals(account.getCreationDate(), responseDTO.getCreationDate()),
                ()-> assertEquals(account.getBalance(), responseDTO.getBalance()),
                ()-> assertEquals(account.getTransactions(), responseDTO.getTransactions())
        );

    }
}
