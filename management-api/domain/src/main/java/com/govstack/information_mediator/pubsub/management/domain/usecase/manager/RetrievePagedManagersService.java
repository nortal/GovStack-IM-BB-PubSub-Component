package com.govstack.information_mediator.pubsub.management.domain.usecase.manager;

import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class RetrievePagedManagersService implements RetrievePagedManagersUseCase {

    private final FetchManagersPort fetchManagersPort;

    @Override
    public Response execute(Request request) {
        return new Response(fetchManagersPort.fetchManagers(request.pageRequest()));
    }
}
