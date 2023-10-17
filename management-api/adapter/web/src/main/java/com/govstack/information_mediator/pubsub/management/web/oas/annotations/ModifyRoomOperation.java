package com.govstack.information_mediator.pubsub.management.web.oas.annotations;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.ApiErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.MANAGER_NOT_FOUND;
import static com.govstack.information_mediator.pubsub.commons.validation.Violation.ROOM_NOT_FOUND;
import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponses({
    @ApiResponse(responseCode = "200", description = "Returns 200 when room is successfully modified"),
    @ApiResponse(
        responseCode = "404",
        description = "Returns 404 when manager or room is not found",
        content = {
            @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ApiErrorResponse.class),
                examples = {
                    @ExampleObject(
                        name = "Manager not found",
                        value = "{ \"timestamp\": \"2023-08-11T15:53:57.5425895\", \"message\": \"" + MANAGER_NOT_FOUND + "\" }"),
                    @ExampleObject(
                        name = "Room not found",
                        value = "{ \"timestamp\": \"2023-08-11T15:53:57.5425895\", \"message\": \"" + ROOM_NOT_FOUND + "\" }")
                })
        }),
    @ApiResponse(
        responseCode = "400",
        description = "Bad request"),
    @ApiResponse(
        responseCode = "500",
        description = "Internal server error",
        content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiErrorResponse.class))
        })
})
public @interface ModifyRoomOperation {
}
