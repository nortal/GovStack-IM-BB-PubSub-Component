package com.govstack.information_mediator.pubsub.domain.view;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class RoomView {
    private UUID id;
    private String identifier;
    private String managerIdentifier;
}
