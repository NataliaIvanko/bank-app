package com.telran.bankappfirsttry.repository;

import com.telran.bankappfirsttry.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
