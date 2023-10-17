package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ExportManagersService implements ExportManagersUseCase {

    private final FetchManagersPort fetchManagersPort;

    @Override
    public Response execute() {
        return new Response(fetchManagersPort.fetchAllManagers());
    }
}
