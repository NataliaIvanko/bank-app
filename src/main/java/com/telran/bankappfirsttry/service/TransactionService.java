package com.telran.bankappfirsttry.service;

import com.telran.bankappfirsttry.entity.Transaction;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TransactionService {
   ResponseEntity<Transaction> getTransactionByID(Long id);

  //  List<Transaction> getTransactionsByFilters(String type, LocalDateTime date, String sort, Transaction transaction);
    List<Transaction> getTransactionsFiltered(Transaction transaction);

}
