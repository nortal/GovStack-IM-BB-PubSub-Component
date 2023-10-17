package com.govstack.information_mediator.pubsub.management.web.auth;

import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.auth.AuthenticationInfoPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
class AuthenticationInfoProvider implements AuthenticationInfoPort {

    private static final String MANAGEMENT_API = "MANAGEMENT-API";

    private final UserContext userContext;

    @Override
    public String getName() {
        return Optional.ofNullable(userContext)
            .map(UserContext::getUsername)
            .orElse(MANAGEMENT_API);
    }
}
