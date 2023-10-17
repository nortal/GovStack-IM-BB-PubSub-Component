package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.management.domain.auth.AuthenticationInfoPort;
import com.govstack.information_mediator.pubsub.shared.jooq.AuditInformation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuditInformationProvider implements AuditInformation {

    private final AuthenticationInfoPort authenticationInfoPort;

    @Override
    public String getName() {
        return authenticationInfoPort.getName();
    }
}
