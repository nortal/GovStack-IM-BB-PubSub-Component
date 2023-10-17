package com.govstack.information_mediator.pubsub.commons.oas.annotations;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
    responseCode = "500",
    description = "Internal server error",
    content = {@Content(
        mediaType = "application/json",
        schema = @Schema(
            implementation = ApiErrorResponse.class))}
)
public @interface InternalErrorApiResponse {}
