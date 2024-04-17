package com.pragma.arquetipobootcamp2024.configuration.exceptionhandler;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.RepeatedItemsInTheListException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.IllegalArgException;
import com.pragma.arquetipobootcamp2024.configuration.Constants;
import com.pragma.arquetipobootcamp2024.domain.exception.EmptyFieldException;
import com.pragma.arquetipobootcamp2024.domain.exception.NegativeNotAllowedException;
import com.pragma.arquetipobootcamp2024.domain.util.DomainConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {
    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<ExceptionResponse> handleEmptyFieldException(EmptyFieldException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.EMPTY_FIELD_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(NegativeNotAllowedException.class)
    public ResponseEntity<ExceptionResponse> handleNegativeNotAllowedException(NegativeNotAllowedException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.NEGATIVE_NOT_ALLOWED_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ExceptionResponse(message == null ? DomainConstants.INVALID_FIELD : message,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleElementAlreadyExistsException(ElementAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.ELEMENT_ALREADY_EXISTS_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(RepeatedItemsInTheListException.class)
    public ResponseEntity<ExceptionResponse> handleRepeatedItemsInTheListException(RepeatedItemsInTheListException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.REPEATED_LIST_CONTENT_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(IllegalArgException.class)
    public ResponseEntity<ExceptionResponse> handleIllegalArgumentException(IllegalArgException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ExceptionResponse(
                exception.getMessage(), HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
}