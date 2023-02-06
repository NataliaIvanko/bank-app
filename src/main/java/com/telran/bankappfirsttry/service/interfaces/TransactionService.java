package com.telran.bankappfirsttry.service.interfaces;

import com.telran.bankappfirsttry.dto.TransactionRequestDTO;
import com.telran.bankappfirsttry.dto.TransactionResponseDTO;

import java.util.List;

public interface TransactionService {
   TransactionResponseDTO getTransactionByID(Long id);

    List<TransactionResponseDTO> getTransactionsFiltered(TransactionRequestDTO transaction, String sort);
    List<TransactionResponseDTO> getAllTransactions (TransactionRequestDTO requestDTO);

}
