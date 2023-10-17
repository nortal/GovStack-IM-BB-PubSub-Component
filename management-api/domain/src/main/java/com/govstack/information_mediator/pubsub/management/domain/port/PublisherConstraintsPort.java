package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;

import java.util.List;
import java.util.UUID;

public interface PublisherConstraintsPort {

    List<UUID> getPublisherEventTypeIds(PublisherID publisherID);

    void removeEventTypeConstraint(PublisherID publisherID, UUID eventTypeId);

    void addEventTypeConstraint(PublisherID publisherID, UUID eventTypeId);
}
