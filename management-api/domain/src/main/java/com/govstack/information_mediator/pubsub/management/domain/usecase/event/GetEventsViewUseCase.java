package com.govstack.information_mediator.pubsub.management.domain.usecase.event;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.EventView;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

public interface GetEventsViewUseCase {

    Response execute(Request request);

    @Value
    @Builder
    class Request {
        ManagerIdentifier manager;
        RoomIdentifier room;
        PublisherID publisher;
        Instant fromDate;
        Instant toDate;
        PageRequest pageRequest;
        String publisherIdentifier;
        String eventTypeIdentifier;
        Integer eventTypeVersion;
    }

    record Response(Page<EventView> eventsPage) {

    }
}
