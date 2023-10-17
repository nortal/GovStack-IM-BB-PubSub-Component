package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public interface GetEventTypeVersionsUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        RoomIdentifier roomIdentifier;
        ManagerIdentifier managerIdentifier;
        EventTypeIdentifier eventTypeIdentifier;
    }

    record Response(List<EventTypeVersionOverview> eventTypeVersions) {

    }
}
