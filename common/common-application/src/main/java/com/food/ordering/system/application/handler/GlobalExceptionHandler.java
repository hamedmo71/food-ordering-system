package com.food.ordering.system.application.handler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorDTO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ErrorDTO.builder()
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(e.getMessage())
                .build();
    }

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidationException(ValidationException validationException) {
        ErrorDTO errorDTO;
        log.error(validationException.getMessage(), validationException);
        if (validationException instanceof ConstraintViolationException exception) {

            String violations = extractViolationFromException(exception);

            errorDTO = ErrorDTO.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message(violations)
                    .build();
        } else {
            errorDTO = ErrorDTO.builder()
                    .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                    .message(validationException.getMessage())
                    .build();
        }
        return errorDTO;
    }

    private String extractViolationFromException(ConstraintViolationException exception) {
        return exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.joining("--"));

    }
}
