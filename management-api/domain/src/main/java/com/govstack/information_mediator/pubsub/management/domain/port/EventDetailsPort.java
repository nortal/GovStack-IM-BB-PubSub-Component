package com.govstack.information_mediator.pubsub.management.domain.port;

import com.govstack.information_mediator.pubsub.domain.entity.id.EventID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.management.domain.entity.EventDetails;

import java.util.Optional;

public interface EventDetailsPort {

    Optional<EventDetails> resolveEventDetails(EventID eventID, RoomID roomID);
}
