package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import lombok.Builder;
import lombok.Value;

public interface CreateEventTypesUseCase {

    void execute(Request request);

    @Value
    @Builder
    class Request {
        RoomData room;
        ManagerData manager;
        EventTypeData eventType;

        @Value(staticConstructor = "of")
        public static class RoomData {
            String identifier;
        }

        @Value(staticConstructor = "of")
        public static class ManagerData {
            String identifier;
        }

        @Value
        @Builder
        public static class EventTypeData {
            String identifier;
            VersionData version;


            @Value(staticConstructor = "of")
            public static class VersionData {
                Integer versionNo;
                String jsonSchema;
            }
        }
    }
}
