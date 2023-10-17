package com.govstack.information_mediator.pubsub.management.web.oas.annotations;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.InternalErrorApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ManagerNotFoundApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomNotFoundApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.SubscriptionNotFoundApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@ApiResponse(responseCode = "200", description = "List of all events for a subscription")
@RoomNotFoundApiResponse
@ManagerNotFoundApiResponse
@SubscriptionNotFoundApiResponse
@InternalErrorApiResponse
@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GetSubscriptionEventsOperation {
}
