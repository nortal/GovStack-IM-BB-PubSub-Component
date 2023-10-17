package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.EVENT_TYPE_NOT_FOUND;

public class EventTypeNotFoundException extends ResourceNotFoundException{
    public EventTypeNotFoundException() {
        super(EVENT_TYPE_NOT_FOUND);
    }
}
