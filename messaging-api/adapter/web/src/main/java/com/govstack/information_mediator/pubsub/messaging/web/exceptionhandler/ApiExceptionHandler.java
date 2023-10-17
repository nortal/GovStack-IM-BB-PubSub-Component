package com.govstack.information_mediator.pubsub.messaging.web.exceptionhandler;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.govstack.information_mediator.pubsub.commons.exception.ResourceNotFoundException;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ApiErrorResponse;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response.Status;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.RestResponse.ResponseBuilder;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.lang.reflect.UndeclaredThrowableException;
import java.time.LocalDateTime;
import java.util.UUID;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON_TYPE;
import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.INTERNAL_SERVER_ERROR;
import static jakarta.ws.rs.core.Response.Status.NOT_FOUND;

@Slf4j
class ApiExceptionHandler {

    @Produces(MediaType.APPLICATION_JSON)
    @ServerExceptionMapper(ResourceNotFoundException.class)
    public RestResponse<ApiErrorResponse> handleResourceFoundException(ResourceNotFoundException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return toRestResponse(NOT_FOUND, response);
    }

    @ServerExceptionMapper(ApiException.class)
    public RestResponse<ApiErrorResponse> handleApiException(ApiException e) {
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
        return toRestResponse(BAD_REQUEST, response);
    }


    @ServerExceptionMapper(UndeclaredThrowableException.class)
    public RestResponse<ApiErrorResponse> handleUndeclaredThrowableException(UndeclaredThrowableException e) {
        if (e.getCause() instanceof JsonMappingException) {
            ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), e.getMessage());
            return toRestResponse(INTERNAL_SERVER_ERROR, response);
        }
        return handleGenericException(e);
    }

    @ServerExceptionMapper(InternalErrorException.class)
    public RestResponse<ApiErrorResponse> handleInternalException(InternalErrorException e) {
        return handleGenericException(e);
    }

    @ServerExceptionMapper(Exception.class)
    public RestResponse<ApiErrorResponse> handleGenericException(Exception e) {
        String exceptionId = UUID.randomUUID().toString();
        ApiErrorResponse response = new ApiErrorResponse(LocalDateTime.now(), "Technical error: " + exceptionId);
        log.error(exceptionId, e);
        return toRestResponse(INTERNAL_SERVER_ERROR, response);
    }

    private static <T> RestResponse<T> toRestResponse(Status status, T body) {
        return ResponseBuilder.create(status, body).type(APPLICATION_JSON_TYPE).build();
    }
}
