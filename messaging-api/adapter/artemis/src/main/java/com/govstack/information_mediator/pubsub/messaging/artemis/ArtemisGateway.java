package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.EventMessage;
import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PullEventMessagesPort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
public class ArtemisGateway implements PullEventMessagesPort, PublishEventMessagesPort {

    private final ArtemisConsumer artemisConsumer;
    private final ArtemisProducer artemisProducer;
    private final ArtemisDestinationService artemisDestinationService;
    private final ArtemisMessageFactory artemisMessageFactory;
    private final ArtemisEBRMessageFactory artemisEBRMessageFactory;

    @Override
    public Optional<EventMessage> pullEventMessage(Subscription subscription) {
        var destination = artemisDestinationService.resolveDestination(subscription);
        return artemisConsumer.receiveMessage(destination)
            .map(message -> EventMessage.builder()
                .eventId(message.getProperties().getEventId())
                .subscriptionId(message.getProperties().getSubscriptionId())
                .payload(message.getPayload())
                .hasAnother(artemisConsumer.hasMoreMessages(destination))
                .build());
    }

    @Override
    public void publishEvent(Room room, List<Subscription> subscriptions, Event event, String payload) {
        artemisProducer.sendMessages(artemisMessageFactory.createMessages(room, subscriptions, event, payload));
    }

    @Override
    public void republishEvent(PushEventMessage eventMessage, Subscription subscription, Room room) {
        artemisEBRMessageFactory.createMessage(room, subscription, eventMessage).ifPresent(artemisProducer::sendMessage);
    }
}
