package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Transaction> getTransactionByID(@PathVariable("id") Long id) {
        return transactionService.getTransactionByID(id);


    }

    @PostMapping("/transactions/search")
    public List<Transaction> getTransactionsFiltered(  @RequestBody Transaction transaction,
                                                       @RequestParam(value = "sort", required = false) String sort){
        return transactionService.getTransactionsFiltered(transaction, sort);
    }

     /*
    @GetMapping("/transaction")
    public List<Transaction> getTransactionsFiltered(@RequestParam(value = "type", required = false) String type,
                                                     @RequestParam(value = "date", required = false) LocalDateTime date,
                                                     @RequestParam(value = "sort", required = false) String sort,
                                                     @RequestBody Transaction transaction){
        return transactionService.getTransactionsByFilters(type, date,sort, transaction);
    }

     */
}
