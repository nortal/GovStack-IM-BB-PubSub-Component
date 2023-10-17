package com.govstack.information_mediator.pubsub.messaging.domain.port;

import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import lombok.Builder;
import lombok.Value;

import java.util.List;

public interface PublishEventMessagesPort {

    void publishEvent(Room room, List<Subscription> subscriptions, Event event, String payload);

    void republishEvent(PushEventMessage eventMessage, Subscription subscription, Room room);

    @Value
    @Builder
    class SubscribedEvent {
        Room room;
        Event event;
        String content;
        Publisher publisher;
        Subscription subscription;
    }
}
