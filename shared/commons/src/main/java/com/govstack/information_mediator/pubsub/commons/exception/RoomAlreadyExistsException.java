package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.ROOM_ALREADY_EXISTS;

public class RoomAlreadyExistsException extends DuplicateResourceException {

    public RoomAlreadyExistsException() {
        super(ROOM_ALREADY_EXISTS);
    }

    public RoomAlreadyExistsException(Throwable cause) {
        super(ROOM_ALREADY_EXISTS, cause);
    }
}
