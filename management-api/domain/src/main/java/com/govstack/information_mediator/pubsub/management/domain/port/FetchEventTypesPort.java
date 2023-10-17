package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeVersionID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeVersionOverview;

import java.util.List;
import java.util.Optional;

public interface FetchEventTypesPort {

    Optional<EventType> fetchEventType(String identifier, Room room);

    Optional<EventTypeID> fetchEventTypeID(EventTypeIdentifier eventTypeIdentifier, RoomID roomID);

    Optional<EventTypeVersionID> fetchEventTypeVersionID(EventTypeID eventTypeID, Integer versionNo);

    Integer countEventTypeVersions(EventTypeID eventTypeID);

    Page<EventTypeView> fetchEventTypeViews(RoomID roomID, PageRequest pageRequest);

    List<EventTypeVersionOverview> fetchEventTypeVersionOverviews(EventTypeID eventTypeID);

    List<String> fetchEventTypeIdentifiers(RoomID roomID);
}
