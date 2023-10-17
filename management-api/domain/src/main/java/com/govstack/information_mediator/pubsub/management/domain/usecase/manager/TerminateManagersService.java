package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.TerminateManagersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@RequiredArgsConstructor
class TerminateManagersService implements TerminateManagersUseCase {

    private final FetchManagersPort fetchManagersPort;
    private final TerminateManagersPort terminateManagersPort;

    @Override
    @Transactional
    public void execute(Request request) {
        terminateManagersPort.terminate(fetchManager(request.id()));
    }

    private Manager fetchManager(UUID managerId) {
        return fetchManagersPort.fetchManager(managerId).orElseThrow(ManagerNotFoundException::new);
    }
}
