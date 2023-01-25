package com.telran.bankappfirsttry.entity;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder

@Entity
@Table(name = "transaction")
public class Transaction {

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;

    @Column(name = "date_time")
    private Instant dateTime;

    @Column(name = "transaction_type")
    private TransactionType type;

    @Column(name = "amount")
    private Float amount;

    @Column(name = "account_from")
    private Long accountFrom;

    @Column(name = "account_to")
    private Long accountTo;


    //@ManyToMany(mappedBy = "transactions") or
    //Set<Account> ??;



}
