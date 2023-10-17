package com.govstack.information_mediator.pubsub.management.domain.usecase.publisher;

import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;

public interface TerminatePublisherUseCase {

    void execute(Request request);

    record Request(ManagerIdentifier manager, RoomIdentifier room, PublisherID publisher) {

    }
}
