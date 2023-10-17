package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

public interface EventConsumerUseCase {

    void execute(Request request);

    @Value
    @Builder
    class Request {
        UUID roomId;
        UUID eventTypeId;
        UUID publisherId;
        String payload;
    }
}
