package com.telran.bankappfirsttry.enums;

import com.telran.bankappfirsttry.entity.enums.TransactionType;
import com.telran.bankappfirsttry.entity.enums.TransactionTypeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransactionTypeConverterTest {
    private static final TransactionTypeConverter CONVERTER = new TransactionTypeConverter();


    @Test
    @DisplayName("should convert to string")
    public void shouldConvertToStringWhenValueStatusWithdraw() {
        TransactionType givenType = TransactionType.WITHDRAW;
        String expected = givenType.getTypeName().toUpperCase();
        var actual = CONVERTER.convertToDatabaseColumn(givenType);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    @DisplayName("should return a type withdraw when string = withdraw")
    public void shouldReturnTypeWithdrawWhenStringIsWithdraw() {
        TransactionType expected = TransactionType.WITHDRAW;
        String typeActual = "withdraw";
        Assertions.assertEquals(expected, TransactionType.findByType(typeActual));
    }

    @Test
    @DisplayName("should return a type deposit when string = deposit")
    public void shouldReturnTypeDepositWhenStringIsDeposit() {
        TransactionType expected = TransactionType.DEPOSIT;
        String typeActual = "deposit";
        Assertions.assertEquals(expected, TransactionType.findByType(typeActual));

    }

    @Test
    @DisplayName("should return a type transfer when string = transfer")
    public void shouldReturnTypeTransferWhenStringIsTransfer() {
        TransactionType expected = TransactionType.TRANSFER;
        String typeActual = "transfer";
        Assertions.assertEquals(expected, TransactionType.findByType(typeActual));
    }
}
