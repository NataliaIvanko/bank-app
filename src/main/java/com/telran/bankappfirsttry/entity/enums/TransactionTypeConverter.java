package com.telran.bankappfirsttry.entity.enums;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionTypeConverter implements AttributeConverter<TransactionType, String> {

    @Override
    public String convertToDatabaseColumn(TransactionType transactionType) {
        System.out.println(transactionType);
        return transactionType == null ? null : transactionType.getTypeName().toUpperCase();

    }

    @Override
    public TransactionType convertToEntityAttribute(String s) {
        return s == null ? null : TransactionType.findByType(s.toUpperCase());
    }
}
