package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.ROOM_NOT_FOUND;

public class RoomNotFoundException extends ResourceNotFoundException {
    public RoomNotFoundException() {
        super(ROOM_NOT_FOUND);
    }
}
