package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.PUBLISHER_NOT_FOUND;

public class PublisherNotFoundException extends ResourceNotFoundException {
    public PublisherNotFoundException() {super(PUBLISHER_NOT_FOUND);}
}
