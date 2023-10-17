package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import lombok.Builder;
import lombok.Value;

public interface CreateEventTypeVersionUseCase {

    void execute(Request request);

    @Value
    @Builder
    class Request {
        RoomIdentifier roomIdentifier;
        ManagerIdentifier managerIdentifier;
        EventTypeIdentifier eventTypeIdentifier;
        VersionData version;

        @Value
        @Builder
        public static class VersionData {
            Integer versionNo;
            String jsonSchema;
        }
    }
}
