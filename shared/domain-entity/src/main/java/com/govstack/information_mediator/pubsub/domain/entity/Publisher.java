package com.govstack.information_mediator.pubsub.domain.entity;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class Publisher {
    private UUID id;
    private UUID roomId;
    private String identifier;
    private IdentifierType identifierType;
}
