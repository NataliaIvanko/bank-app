package com.telran.bankappfirsttry.testDto;

import com.telran.bankappfirsttry.repository.AccountRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountRequestDtoTest {

    @Mock
    AccountRepository repository;
}
