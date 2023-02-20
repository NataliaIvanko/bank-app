package com.telran.bankappfirsttry.serviceImpl;

import com.telran.bankappfirsttry.dto.TransactionResponseDTO;
import com.telran.bankappfirsttry.entity.Transaction;
import com.telran.bankappfirsttry.mapper.TransactionMapper;
import com.telran.bankappfirsttry.repository.TransactionRepository;
import com.telran.bankappfirsttry.service.impl.TransactionServiceImpl;
import com.telran.bankappfirsttry.util.DtoCreator;
import com.telran.bankappfirsttry.util.EntityCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
/*



 */

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionMapper mapper;

    @InjectMocks
    TransactionServiceImpl service;

    @Nested
    @DisplayName("GetTransactionById()")
    public class getTransactionById{
    @Test
    @DisplayName("should throw 404 not found when there is no such a transaction")
    public void shouldThrowNotFoundWhenThereIsNoSuchATransaction() {
        Long requestedId = 1L;
        HttpStatus expectedStatus = HttpStatus.NOT_FOUND;
        String expectedMessage = "transaction is not found";

        Mockito
                .when(transactionRepository.findById(requestedId))
                .thenReturn(Optional.empty());
        ResponseStatusException exception = Assertions.assertThrows(
                ResponseStatusException.class,()-> service.getTransactionByID(requestedId)
        );
        Assertions.assertEquals(expectedStatus, exception.getStatus());
        Assertions.assertEquals(expectedMessage, exception.getReason());
    }

    @Test
    @DisplayName("should return dto when there is such a transaction")
    public void shouldReturnDtoWhenThereIsSuchATransaction(){
     Long requestedId = 1L;
        Transaction transaction = EntityCreator.getTransaction();
        TransactionResponseDTO expectedResponseDTO = DtoCreator.getTransactionResponseDto();

        Mockito
                .when(transactionRepository.findById(requestedId))
                .thenReturn(Optional.of(transaction));
        Mockito
                .when(mapper.transactionToDto(transaction))
                .thenReturn(expectedResponseDTO);

        TransactionResponseDTO actualResponseDto = service.getTransactionByID(requestedId);
        Assertions.assertEquals(expectedResponseDTO, actualResponseDto);
    }
    }
    @Nested
    @DisplayName("getAllTransactions()")
    public class getAllTransactions{
    @Test
    @DisplayName("should return empty list when there are no transactions")
    public void shouldReturnEmptyListWhenThereAreNoTransactions() {
          int expectedResponseSize = 0;

        Mockito
                .when(transactionRepository.findAll())
                .thenReturn(List.of());
        var response = service.getAllTransactions();
          Assertions.assertEquals(expectedResponseSize, response.size());
    //    Assertions.assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("should return list of transaction when there are transactions")
    public void shouldReturnListOfTransactions() {
     List<Transaction> transactionList = List.of(EntityCreator.getTransaction());
     List<TransactionResponseDTO> expectedResponseDto = List.of(DtoCreator.getTransactionResponseDto());
            Mockito
                    .when(transactionRepository.findAll())
                    .thenReturn(transactionList);
            Mockito
                    .when(mapper.transactionListToDto(transactionList))
                    .thenReturn(expectedResponseDto);
            List<TransactionResponseDTO> actualResponseDto = service.getAllTransactions();
            Assertions.assertFalse(actualResponseDto.isEmpty());
            Assertions.assertEquals(expectedResponseDto, actualResponseDto);
    }
    }


}
