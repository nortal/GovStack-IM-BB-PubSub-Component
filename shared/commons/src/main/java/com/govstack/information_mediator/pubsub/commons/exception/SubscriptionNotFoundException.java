package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.SUBSCRIPTION_NOT_FOUND;

public class SubscriptionNotFoundException extends ResourceNotFoundException {
    public SubscriptionNotFoundException() {
        super(SUBSCRIPTION_NOT_FOUND);
    }
}
