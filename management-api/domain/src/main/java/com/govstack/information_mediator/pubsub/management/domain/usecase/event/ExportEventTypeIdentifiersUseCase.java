package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

import java.util.List;

public interface ExportEventTypeIdentifiersUseCase {

    Response execute(Request request);

    record Request(ManagerIdentifier manager, RoomIdentifier room) {

    }

    record Response(List<String> eventTypes) {

    }
}
