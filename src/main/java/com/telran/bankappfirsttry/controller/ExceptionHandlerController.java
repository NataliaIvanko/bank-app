package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.dto.ErrorResponseDTO;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponseDTO handleErrors(ResponseStatusException ex){
        return ErrorResponseDTO.builder()
                .status(ex.getStatus())
                .message(ex.getReason())
                .build();
    }
}
