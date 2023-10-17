package com.govstack.information_mediator.pubsub.management.domain.auth;

public interface UserContext {

    String getUsername();

    void setUsername(String username);

    void clear();
}
