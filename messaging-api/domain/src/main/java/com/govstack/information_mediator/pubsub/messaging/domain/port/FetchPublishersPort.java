package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.Room;

import java.util.Optional;

public interface FetchPublishersPort {

    Optional<Publisher> fetchPublisher(Room room, String identifier);

}
