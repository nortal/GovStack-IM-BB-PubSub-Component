package com.govstack.information_mediator.pubsub.commons.exception;

public class EventDeliveryException extends RuntimeException {

    public EventDeliveryException(String message) {
        super(message);
    }

    public EventDeliveryException(String message, Throwable cause) {
        super(message, cause);
    }
}
