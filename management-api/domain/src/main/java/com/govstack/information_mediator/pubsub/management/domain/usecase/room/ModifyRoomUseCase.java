package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public interface ModifyRoomUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomData room;
        ManagerData manager;
        String newManagerIdentifier;
        Room newRoomData;

        @Value
        @Builder
        public static class ManagerData {
            String identifier;
        }

        @Value
        @Builder
        public static class RoomData {
            String identifier;
        }
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Response {
        Room room;
        Manager manager;
    }

}
