package com.govstack.information_mediator.pubsub.management.domain.usecase.member;

import com.govstack.information_mediator.pubsub.management.domain.port.FetchMembersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ListMembersService implements ListMembersUseCase {

    private final FetchMembersPort fetchMembersPort;

    @Override
    public Response execute(Request request) {
        return new Response(fetchMembersPort.fetchMembers(request.getPageRequest()));
    }

}
