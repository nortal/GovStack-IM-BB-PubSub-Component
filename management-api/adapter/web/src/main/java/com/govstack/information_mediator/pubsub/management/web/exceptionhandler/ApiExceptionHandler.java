package com.govstack.information_mediator.pubsub.management.web.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.exception.DuplicateResourceException;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.govstack.information_mediator.pubsub.commons.exception.ResourceNotFoundException;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ApiErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateResourceException(DuplicateResourceException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BusinessViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleBusinessViolationException(BusinessViolationException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleResourceNotFoundException(ResourceNotFoundException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiErrorResponse> handleApiException(ApiException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UndeclaredThrowableException.class)
    public ResponseEntity<ApiErrorResponse> handleUndeclaredThrowableException(UndeclaredThrowableException e) {
        if (e.getCause() instanceof JsonMappingException) {
            ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase());
            return new ResponseEntity<>(response, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return handleGenericException(e);
    }

    @ExceptionHandler(InternalErrorException.class)
    public ResponseEntity<ApiErrorResponse> handleInternalException(Exception e) {
        return handleGenericException(e);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGenericException(Exception e) {
        String exceptionId = UUID.randomUUID().toString();
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), "Technical error: " + exceptionId);
        log.error(exceptionId, e);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
