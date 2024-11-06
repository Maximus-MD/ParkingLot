package com.endava.md.internship.parkinglot.exception;

import com.endava.md.internship.parkinglot.dto.RegistrationResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RegistrationResponseDto> handleValidationExceptions(MethodArgumentNotValidException exception) {
        Set<String> errors = new HashSet<>();
        exception.getBindingResult().getAllErrors().forEach(error -> {errors.add(error.getDefaultMessage());});
        Set<Integer> resultErrors= errors.stream().map(Integer::parseInt).collect(Collectors.toSet());
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false,null,resultErrors);
        return ResponseEntity.ok(responseDto);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<RegistrationResponseDto> handleGenericError() {
        RegistrationResponseDto responseDto = new RegistrationResponseDto(false,null,Set.of(Integer.parseInt("{message.server-error}")));
        return ResponseEntity.ok(responseDto);
    }
}