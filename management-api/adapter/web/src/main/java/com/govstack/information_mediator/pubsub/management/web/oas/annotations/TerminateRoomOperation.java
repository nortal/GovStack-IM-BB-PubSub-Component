package com.govstack.information_mediator.pubsub.management.web.oas.annotations;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.InternalErrorApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ManagerNotFoundApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomNotFoundApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "204", description = "Successfully terminated an existing active room and all its dependant relations")
@RoomNotFoundApiResponse
@ManagerNotFoundApiResponse
@InternalErrorApiResponse
public @interface TerminateRoomOperation {
}
