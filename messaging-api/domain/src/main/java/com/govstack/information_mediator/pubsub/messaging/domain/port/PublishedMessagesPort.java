package com.govstack.information_mediator.pubsub.messaging.domain.port;

import java.util.UUID;

public interface PublishedMessagesPort {

    UUID markEventIsPublished(UUID eventId, UUID subscriptionId);

    UUID markEventIsDelivered(UUID eventId, UUID subscriptionId);

    boolean isEventPublished(UUID eventId, UUID subscriptionId);

    boolean isEventDelivered(UUID eventId, UUID subscriptionId);
}
