package com.govstack.information_mediator.pubsub.management.web.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({TYPE})
@Constraint(validatedBy = UpdateEventTypeVersionValidator.class)
public @interface UpdateEventTypeVersionValidation {

    String message() default "Invalid data for updating event type version";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
