package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class TransactionServiceImpl implements TransactionService {

@Autowired
private TransactionRepository transactionRepository;
@Autowired
private  EntityManager entityManager;

    private final List<Transaction> transactionsList = new ArrayList<>();
    @Override
    public ResponseEntity<Transaction> getTransactionByID(Long id) {

        Transaction transaction = null;
        for(Transaction tr : transactionsList){
            if(transaction.getId().equals(id)){
                return ResponseEntity.ok(tr);
            }
        }
        throw new ResponseStatusException(NOT_FOUND, "Transaction not found");
    }


    @Override
    public List<Transaction> getTransactionsFiltered(Transaction transaction) {
        Map<String, Object> searchParams = new HashMap<>();
        StringBuilder query = new StringBuilder();
        // select * from account_details where ad.account.name = :name and ad.country = :country
        query.append("from Transaction tr where 1=1");

        if(transaction.getDateTime() != null){
            query.append(" and tr.dateTime = :dateTime ");
            searchParams.put("dateTime", transaction.getDateTime());
        }
        if(transaction.getType()!= null){
            query.append(" and tr.type = :type ");
            searchParams.put("type", transaction.getType());
        }
        Query emQuery = entityManager.createQuery(query.toString());
        searchParams.forEach((k, v)-> emQuery.setParameter(k, v));

        return emQuery.getResultList();
    }
/*
    @Override
    public List<Transaction> getTransactionsByFilters(String type, LocalDateTime date, String sort, Transaction transaction) {
        Map<String, Object> searchParams = new HashMap<>();
        StringBuilder query = new StringBuilder();
        // select * from account_details where ad.account.name = :name and ad.country = :country
        query.append("from Transaction tr where 1=1");

        if(transaction.getDateTime() != null){
            query.append(" and tr.dateTime = :dateTime ");
            searchParams.put("dateTime", transaction.getDateTime());
        }
        Query emQuery = entityManager.createQuery(query.toString());
        searchParams.forEach((k, v)-> emQuery.setParameter(k, v));

        return emQuery.getResultList();
    }

 */

}
