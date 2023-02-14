package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.dto.TransactionRequestDTO;
import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.service.interfaces.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import java.util.List;

@RestController
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private EntityManager entityManager;

    @GetMapping("/transactions/{id}")
    public TransactionResponseDTO getTransactionByID(@PathVariable("id") Long id) {
        return transactionService.getTransactionByID(id);
    }

    @GetMapping("/transactions")
    public List<TransactionResponseDTO> getAllTransactions() {
        return transactionService.getAllTransactions();
    }

    @PostMapping("/transactions/search")
    public List<TransactionResponseDTO> getTransactionsFiltered(@RequestBody TransactionRequestDTO transaction,
                                                                @RequestParam(value = "sort", required = false) String sort) {
        return transactionService.getTransactionsFiltered(transaction, sort);
    }


}
