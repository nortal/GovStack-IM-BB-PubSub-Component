package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

import java.util.List;

public interface UpdatePublisherUseCase {

    void execute(Request request);

    record Request(
            ManagerIdentifier manager,
            RoomIdentifier room,
            PublisherID publisherID,
            List<String> eventTypes) {

    }
}
