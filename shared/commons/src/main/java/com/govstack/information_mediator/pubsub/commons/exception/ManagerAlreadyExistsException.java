package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.MANAGER_ALREADY_EXISTS;

public class ManagerAlreadyExistsException extends DuplicateResourceException {

    public ManagerAlreadyExistsException() {
        super(MANAGER_ALREADY_EXISTS);
    }

    public ManagerAlreadyExistsException(Throwable cause) {
        super(MANAGER_ALREADY_EXISTS, cause);
    }
}
