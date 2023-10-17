package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

import java.util.List;

public interface CreatePublisherUseCae {

    Response execute(Request request);

    record Request(ManagerIdentifier manager, RoomIdentifier room, PublisherData publisher) {

        public record PublisherData(String identifier, IdentifierType identifierType, List<String> eventTypes) { }
    }

    record Response(PublisherID publisherID) {}
}
