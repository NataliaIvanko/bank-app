package com.telran.bankappfirsttry.controller;

import com.telran.bankappfirsttry.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler(ResponseStatusException.class)
    public ErrorResponseDTO handleErrors(ResponseStatusException ex){
        return ErrorResponseDTO.builder()
                .status(ex.getStatus())
                .message(ex.getReason())
                .statusCode(HttpStatus.NOT_FOUND.value())
                .build();
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponseDTO getInputValidationErrorResponse(MethodArgumentNotValidException e){
//Map<field, list<error messages>
// email - not blank,email,  length
        Map<String, List<String>> errors =   e.getFieldErrors().stream()
                .collect(Collectors.groupingBy(x-> x.getField(),
                        Collectors.mapping(y -> y.getDefaultMessage(),
                                Collectors.toList())));

        return ErrorResponseDTO.builder()
                .message("Input validation error")
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .errors(errors)
                .build();


    }
}
