package com.govstack.information_mediator.pubsub.messaging.web.auth;

import com.govstack.information_mediator.pubsub.messaging.domain.auth.AuthenticationInfoPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;

@ApplicationScoped
class AuthenticationInfoProvider implements AuthenticationInfoPort {

    private static final String MESSAGING_API = "MESSAGING-API";

    @Inject
    XRoadContext xRoadContext;

    @Override
    public String getName() {
        return Optional.ofNullable(xRoadContext)
            .map(XRoadContext::getClient)
            .orElse(MESSAGING_API);
    }
}
