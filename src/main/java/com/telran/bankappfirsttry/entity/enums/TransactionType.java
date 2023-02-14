package com.telran.bankappfirsttry.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter

public enum TransactionType {

    WITHDRAW(1, "withdraw"),
    TRANSFER(2, "transfer"),
    DEPOSIT(3, "deposit");

    private final Integer typeId;
    private final String typeName;



   @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public static TransactionType findByType(String typeName) {
        if (typeName == null) {
            return null;
        }
        return Arrays.stream(TransactionType.values())
                .filter(x -> x.getTypeName().equalsIgnoreCase(typeName))
                .findFirst()
                .orElse(null);
    }

}
