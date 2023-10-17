package com.govstack.information_mediator.pubsub.management.domain.usecase.member;

import com.govstack.information_mediator.pubsub.management.domain.port.FetchMembersPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExportMembersService implements ExportMembersUserCase {

    private final FetchMembersPort fetchMembersPort;

    @Override
    public Response execute() {
        return new Response(fetchMembersPort.fetchMembers());
    }
}
