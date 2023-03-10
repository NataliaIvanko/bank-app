package com.telran.bankappfirsttry.service.impl;

import com.telran.bankappfirsttry.dto.TransactionRequestDTO;
import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.mapper.TransactionMapper;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.interfaces.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
@AllArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private TransactionRepository transactionRepository;
    private EntityManager entityManager;
    private final TransactionMapper transactionMapper;

    @Override
    public TransactionResponseDTO getTransactionByID(Long id) {
        var transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "transaction is not found"));
        return transactionMapper.transactionToDto(transaction);
    }


    @Override
    public List<TransactionResponseDTO> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll().stream()
                .sorted(Comparator.comparing(Transaction::getId))
                .toList();
        return transactionMapper.transactionListToDto(transactions);
    }

    @Override
    public List<TransactionResponseDTO> getTransactionsFiltered(TransactionRequestDTO transaction, String sort) {
        Map<String, Object> searchParams = new HashMap<>();
        StringBuilder query = new StringBuilder();
        query.append("from Transaction tr where 1=1");

        if (transaction.getDateTime() != null) {
            query.append(" and tr.dateTime = :dateTime ");
            searchParams.put("dateTime", transaction.getDateTime());
        }
        if (transaction.getType() != null) {
            query.append(" and tr.type = :type ");
            System.out.println(transaction.getType());
            searchParams.put("type", transaction.getType());

        }
        if (transaction.getType() != null && sort != null) {
            if (sort.equals("-creationDate")) {
                query.append(" and tr.type = :type ORDER BY tr.dateTime DESC ");
            }
            if (sort.equals("creationDate")) {
                query.append(" and tr.type = :type ORDER BY tr.dateTime ASC ");
            }
            searchParams.put("type", transaction.getType());
        }

        Query emQuery = entityManager.createQuery(query.toString());
        searchParams.forEach((k, v) -> emQuery.setParameter(k, v));

        return emQuery.getResultList();
    }
}
