package com.telran.bankappfirsttry.repository;

import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.entity.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByDateTime(Instant dateTime);

    List<Transaction> findAllByType(TransactionType type);

    List<Transaction> findAllByTypeAndDateTime(TransactionType type, Instant dateTime);


}
