package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.domain.view.RoomDetailedView;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

public interface GetRoomDetailsUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomData room;
        ManagerData manager;

        @Value
        @Builder
        @RequiredArgsConstructor(staticName = "of")
        public static class ManagerData {
            String identifier;
        }

        @Value
        @Builder
        @RequiredArgsConstructor(staticName = "of")
        public static class RoomData {
            String identifier;
        }
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Response {
        RoomDetailedView room;
        Manager manager;
    }

}
