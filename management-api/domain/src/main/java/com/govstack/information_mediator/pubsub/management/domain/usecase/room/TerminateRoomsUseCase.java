package com.govstack.information_mediator.pubsub.management.domain.usecase.room;

public interface TerminateRoomsUseCase {

    void execute(Request request);

    record Request(ManagerData manager, RoomData room) {
        public static Request of(String managerIdentifier, String roomIdentifier) {
            return new Request(new ManagerData(managerIdentifier), new RoomData(roomIdentifier));
        }

        public record ManagerData(String identifier) { }

        public record RoomData(String identifier) { }
    }
}
