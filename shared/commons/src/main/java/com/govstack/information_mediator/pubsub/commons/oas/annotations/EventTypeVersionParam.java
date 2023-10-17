package com.govstack.information_mediator.pubsub.commons.oas.annotations;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Target(PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = "The version of the event type", example = "1")
public @interface EventTypeVersionParam {
}
