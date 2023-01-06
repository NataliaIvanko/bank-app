package com.telran.bankappfirsttry.repository;

import com.telran.bankappfirsttry.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

List<Account> findAllByCityInIgnoreCase(List<String> city);
List<Account>findAccountByCreationDate(Instant creationDate);
List<Account>findAccountByCityInAndCreationDate(List<String> city, Instant creationDate);
}
