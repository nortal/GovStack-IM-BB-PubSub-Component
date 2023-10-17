package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.util.UUID;

public interface CreateRoomUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomData room;
        ManagerData manager;

        @Value
        @Builder
        public static class ManagerData {
            String identifier;
        }

        @Value
        @Builder
        public static class RoomData {
            String identifier;
            ConfigurationData configuration;
        }

        @Value
        @Builder
        public static class ConfigurationData {
            Integer messageExpiration;
            Integer deliveryDelay;
            Double deliveryDelayMultiplier;
            Integer deliveryAttempts;
        }
    }

    @Value
    @RequiredArgsConstructor(staticName = "of")
    class Response {
        UUID roomId;
    }
}
