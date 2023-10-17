package com.govstack.information_mediator.pubsub.commons.oas.annotations;

import io.swagger.v3.oas.annotations.Parameter;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;

@Target(PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Parameter(description = "The identifier of the member", example = "EE/BUSINESS/123456789")
public @interface MemberIdParam {
}

