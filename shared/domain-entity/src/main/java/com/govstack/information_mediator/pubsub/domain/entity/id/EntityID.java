package com.govstack.information_mediator.pubsub.domain.entity.id;

import lombok.Getter;

import java.util.UUID;

@Getter
public class EntityID {

    private final UUID id;

    public EntityID(UUID id) {
        this.id = id;
    }
}
