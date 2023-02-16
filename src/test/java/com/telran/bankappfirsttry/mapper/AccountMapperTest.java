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
    @Test
    @DisplayName("mapping accountDto into account")
    public void accountDtoToAccount(){
        AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
        Account expectedAccount = EntityCreator.getAccount();
        Assertions.assertEquals(expectedAccount, accountMapper.dtoToAccount(requestDTO));
    }
    @Test
    @DisplayName("mapping accountDto into account")
    public void accountDtoToAccountPartially() {
        AccountRequestDTO requestDTO = DtoCreator.getAccountRequestDto();
        requestDTO.setFirstName("Jane");
        Account expectedAccount = EntityCreator.getAccount();
        Assertions.assertEquals(expectedAccount.getFirstName(), accountMapper.dtoToAccount(requestDTO).getFirstName());
    }
}
