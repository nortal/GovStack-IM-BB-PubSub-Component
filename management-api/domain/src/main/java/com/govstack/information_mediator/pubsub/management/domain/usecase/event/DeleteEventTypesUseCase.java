package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import lombok.Builder;
import lombok.Data;

public interface DeleteEventTypesUseCase {

   void execute(Request request);

    @Data
    @Builder
    class Request {
        private final RoomData room;
        private final ManagerData manager;
        private final EventTypeData eventType;

        @Data(staticConstructor = "of")
        public static class RoomData {
            private final String identifier;
        }

        @Data(staticConstructor = "of")
        public static class ManagerData {
            private final String identifier;
        }

        @Data(staticConstructor = "of")
        public static class EventTypeData {
            private final String identifier;
        }
    }
}
