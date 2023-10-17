package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;

public interface CreateManagersUseCase {

    Response execute(Request request);

    record Request(String identifier, IdentifierType identifierType) { }

    record Response(Manager manager) { }
}
