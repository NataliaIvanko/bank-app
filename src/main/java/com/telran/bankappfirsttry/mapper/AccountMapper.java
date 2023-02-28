package com.telran.bankappfirsttry.mapper;

import com.telran.bankappfirsttry.dto.AccountRequestDTO;
import com.telran.bankappfirsttry.dto.AccountResponseDTO;
import com.telran.bankappfirsttry.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountResponseDTO accountToDto(Account account);
    Account dtoToAccount(AccountRequestDTO requestDTO);

}
