package com.telran.bankappfirsttry.mapper;

import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.entity.Transaction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {



    List<TransactionResponseDTO>transactionListToDto(List<Transaction>transactions);
    TransactionResponseDTO transactionToDto (Transaction transaction);
}
