package com.govstack.information_mediator.pubsub.commons.exception;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.MANAGER_NOT_FOUND;

public class ManagerNotFoundException extends ResourceNotFoundException {
    public ManagerNotFoundException() {
        super(MANAGER_NOT_FOUND);
    }
}
