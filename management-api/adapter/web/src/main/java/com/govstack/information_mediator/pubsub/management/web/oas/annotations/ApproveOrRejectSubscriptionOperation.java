package com.govstack.information_mediator.pubsub.management.web.oas.annotations;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.InternalErrorApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.SubscriptionNotFoundApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;

@Target(METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200", description = "Change successful")
@ApiResponse(responseCode = "400", description = "Incorrect subscription status provided")
@SubscriptionNotFoundApiResponse
@InternalErrorApiResponse
public @interface ApproveOrRejectSubscriptionOperation {
}
