package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@RequiredArgsConstructor
class ArtemisMessageFactory {

    private final ArtemisDestinationService artemisDestinationService;

    List<ArtemisMessage> createMessages(Room room, List<Subscription> subscriptions, Event event, String payload) {
       return subscriptions.stream()
           .map(subscription -> createMessage(room, subscription, event, payload))
           .collect(Collectors.toList());
    }

    private ArtemisMessage createMessage(Room room, Subscription subscription, Event event, String payload) {
        var timeToLive = Optional.ofNullable(room.getConfiguration().getMessageExpiration())
            .map(Integer::longValue)
            .orElse(0L);

        return ArtemisMessage.builder()
            .headers(ArtemisMessage.Headers.builder()
                .timeToLive(timeToLive)
                .build())
            .properties(ArtemisMessage.Properties.builder()
                .eventId(event.getId())
                .roomId(room.getId())
                .eventTypeId(event.getEventTypeId())
                .eventTypeVersionId(event.getEventTypeVersionId())
                .publisherId(event.getPublisherId())
                .subscriptionId(subscription.getId())
                .correlationId(event.getCorrelationId())
                .build())
            .destination(artemisDestinationService.resolveDestination(subscription))
            .payload(payload)
            .build();
    }
}
