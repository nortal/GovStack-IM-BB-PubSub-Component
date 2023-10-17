package com.govstack.information_mediator.pubsub.domain.entity.identifier;

import lombok.Getter;

@Getter
public class EntityIdentifier {

    private final String identifier;

    public EntityIdentifier(String identifier) {
        this.identifier = identifier;
    }
}
