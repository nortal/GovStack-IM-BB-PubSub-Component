package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;

public interface TerminatePublisherPort {
    void terminate(PublisherID publisherID);
}
