package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_NOT_FOUND;

public class EventNotFound extends ResourceNotFoundException {
    public EventNotFound() {
        super(EVENT_NOT_FOUND);
    }
}
