package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.domain.entity.id.EventID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.entity.EventDetails;

public interface GetEventDetailsUseCase {

    Response execute(Request request);

    record Request(ManagerIdentifier manager, RoomIdentifier room, EventID eventId) {
    }

    record Response(EventDetails eventDetails) {
    }
}
