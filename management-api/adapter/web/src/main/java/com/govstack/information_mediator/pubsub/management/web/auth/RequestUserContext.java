package com.govstack.information_mediator.pubsub.management.web.auth;

import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.INTERFACES)
class RequestUserContext implements UserContext {

    private String username;

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void clear() {
        username = null;
    }
}
