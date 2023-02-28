package com.telran.bankappfirsttry.entity;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "email")
    private String email;

    @Column(name = "creation_date")
    private Instant creationDate;

    @Column(name = "balance")
    private Float balance;

    @ManyToMany
    @JoinTable(name = "accounts_transactions",
            joinColumns = {
                    @JoinColumn(name = "user_id", referencedColumnName = "user_id")},
            inverseJoinColumns = {
                    @JoinColumn(name = "transaction_id", referencedColumnName = "transaction_id")})
    private Set<Transaction> transactions;
}
