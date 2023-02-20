package com.telran.bankappfirsttry.mapper;

import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.util.DtoCreator;
import com.telran.bankappfirsttry.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class TransactionMapperTest {
    @Spy
    private final TransactionMapper mapper = new TransactionMapperImpl();

    @Test
    @DisplayName("mapping transaction list into transaction dto")
    public void transactionListIntoTransactionDto(){
        List<Transaction> listTransaction = new ArrayList<>();
        listTransaction.add(EntityCreator.getTransaction());
        List<TransactionResponseDTO> actualResponseDto = mapper.transactionListToDto(listTransaction);
       compareListToListDto(listTransaction,actualResponseDto);
    }

    @Test //DOES NOT WORK DUE TO MAPPER IMPLEMENTATION WHICH I HAVE NO INFLUENCE TO
    @DisplayName("mapping transaction to transaction response dto")
    public void transactionToTransactionResponseDto(){
        Transaction transaction = EntityCreator.getTransaction();
        TransactionResponseDTO responseDTO = DtoCreator.getTransactionResponseDto();
        Assertions.assertEquals(responseDTO, mapper.transactionToDto(transaction));
    }
    @Test
    @DisplayName("mapping transaction to transaction response dto")
    public void transactionToTransactionResponseDto1(){
        Transaction transaction = EntityCreator.getTransaction();
        TransactionResponseDTO responseDTO =mapper.transactionToDto(transaction);
        compareTransactionToResponseDto(transaction, responseDTO);
    }

    @DisplayName("compare transaction to response dto")
    private void compareTransactionToResponseDto(Transaction transaction, TransactionResponseDTO responseDTO){
        assertAll(
                ()-> assertEquals(transaction.getId(), responseDTO.getId()),
                ()-> assertEquals(transaction.getType(), responseDTO.getType()),
                ()-> assertEquals(transaction.getAmount(), responseDTO.getAmount())
//                ()-> assertEquals(transaction.getAccountFrom(), responseDTO.getAccountFrom),
//                ()-> assertEquals(transaction.getAccountTo(), responseDTO.getAccountTo),
        );
    }
    private void compareListToListDto(List<Transaction>transaction,List<TransactionResponseDTO>responseDTOS){
        assertEquals(transaction.size(), responseDTOS.size());
        for (int s = 0; s < transaction.size(); s++) {
            compareTransactionToResponseDto(transaction.get(s), responseDTOS.get(s));
        }
    }

}


