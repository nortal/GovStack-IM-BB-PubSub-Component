package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.management.domain.view.EventView;

import java.time.Instant;
import java.util.List;

public interface FetchEventsViewPort {

    Page<EventView> fetchEvents(RoomID roomID, Instant start, Instant end, PageRequest pageRequest,
                                String publisherIdentifier, String eventTypeIdentifier, Integer eventTypeVersion);

    Page<EventView> fetchEvents(RoomID roomID, PublisherID publisherID, Instant start, Instant end, PageRequest pageRequest,
                                String eventTypeIdentifier, Integer eventTypeVersion);
}
