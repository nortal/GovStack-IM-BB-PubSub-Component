package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.messaging.domain.auth.AuthenticationInfoPort;
import com.govstack.information_mediator.pubsub.shared.jooq.AuditInformation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
class AuditInformationProvider implements AuditInformation {

    @Inject
    AuthenticationInfoPort authenticationInfoPort;

    @Override
    public String getName() {
        return authenticationInfoPort.getName();
    }
}
