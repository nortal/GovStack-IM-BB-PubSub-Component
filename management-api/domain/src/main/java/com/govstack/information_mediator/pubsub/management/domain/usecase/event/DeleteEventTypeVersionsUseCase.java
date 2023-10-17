package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import lombok.Builder;
import lombok.Value;

public interface DeleteEventTypeVersionsUseCase {

    void execute(Request request);

    @Value
    @Builder
    class Request {
        RoomIdentifier roomIdentifier;
        ManagerIdentifier managerIdentifier;
        EventTypeIdentifier eventTypeIdentifier;
        Integer versionNo;
    }
}
