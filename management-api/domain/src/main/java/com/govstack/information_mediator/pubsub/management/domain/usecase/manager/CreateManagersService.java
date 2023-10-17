package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerAlreadyExistsException;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;

@Component
@RequiredArgsConstructor
class CreateManagersService implements CreateManagersUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final CreateManagersPort createManagersPort;

    @Override
    public Response execute(Request request) {
        if (isManagerAlreadyCreated(request.identifier())) {
            throw new ManagerAlreadyExistsException();
        }
        return new Response(createManager(request.identifier(), request.identifierType()));
    }

    private boolean isManagerAlreadyCreated(String identifier) {
        return fetchManagersPort.fetchManager(identifier).isPresent();
    }

    private Manager createManager(String identifier, IdentifierType identifierType) {
        var manager = Manager.builder()
            .identifier(identifier)
            .identifierType(identifierType)
            .build();

        var id = createManagersPort.createManager(manager);
        manager.setId(id);

        return manager;
    }
}
