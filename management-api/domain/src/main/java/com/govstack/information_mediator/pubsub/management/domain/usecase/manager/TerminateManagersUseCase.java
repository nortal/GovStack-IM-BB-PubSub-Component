package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import java.util.UUID;

public interface TerminateManagersUseCase {

    void execute(Request request);

    record Request(UUID id) { }
}
