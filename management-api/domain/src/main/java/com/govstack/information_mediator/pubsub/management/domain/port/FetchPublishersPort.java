package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;

import java.util.List;
import java.util.UUID;

public interface FetchPublishersPort {

    List<UUID> fetchPublisher(PublisherIdentifier publisherIdentifier, RoomID roomID);

    Page<PublisherView> fetchPublishersView(RoomID roomID, PageRequest pageRequest);

    Page<PublisherView> fetchPublishersView(List<UUID> publisherIDs, PageRequest pageRequest);

    boolean isPublisherInRoom(PublisherID publisherID, RoomID roomID);
}
