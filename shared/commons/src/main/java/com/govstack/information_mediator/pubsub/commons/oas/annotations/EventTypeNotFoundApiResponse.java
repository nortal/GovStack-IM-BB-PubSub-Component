package com.govstack.information_mediator.pubsub.commons.oas.annotations;

import com.govstack.information_mediator.pubsub.commons.validation.Violation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
@ApiResponse(
    responseCode = "404",
    description = Violation.EVENT_TYPE_NOT_FOUND,
    content = {@Content(
        mediaType = "application/json",
        schema = @Schema(
            implementation = ApiErrorResponse.class),
        examples = @ExampleObject(
            name = "EventTypeNotFound",
            value = "{ \"timestamp\": \"2023-08-11T15:53:57.5425895\", \"message\": \"Event type not found\" }"
        ))}
)})
public @interface EventTypeNotFoundApiResponse {}
