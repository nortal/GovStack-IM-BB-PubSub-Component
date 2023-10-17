package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

class ArtemisMessageFactoryTest {

    @Test
    void shouldCreateMessages() {
        // Given
        var destinationResolver = new ArtemisDestinationService();
        destinationResolver.pushEventsQueue = "pubsub-push-events";
        destinationResolver.pullEventsQueueTemplate = "pubsub-pull-events::subscription-%s";

        var messageFactory = new ArtemisMessageFactory(destinationResolver);

        var eventId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var publisherId = UUIDGenerator.randomUUID();
        var correlationId = UUIDGenerator.randomUUID().toString();
        var payload = "Sample test payload";

        var event = Event.builder()
            .id(eventId)
            .eventTypeId(eventTypeId)
            .eventTypeVersionId(eventTypeVersionId)
            .publisherId(publisherId)
            .correlationId(correlationId)
            .build();

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder()
            .id(roomId)
            .configuration(Room.Configuration.builder().messageExpiration(12).build())
            .build();

        var subscriptionId1 = UUIDGenerator.randomUUID();
        var subscription1 = Subscription.builder()
            .id(subscriptionId1)
            .parameters(Parameters.builder().method(PUSH).build())
            .build();

        var subscriptionId2 = UUIDGenerator.randomUUID();
        var subscription2 = Subscription.builder()
            .id(subscriptionId2)
            .parameters(Parameters.builder().method(PULL).build())
            .build();

        var subscriptions = List.of(subscription1, subscription2);

        var destination1 = "pubsub-push-events";
        var destination2 = "pubsub-pull-events::subscription-%s".formatted(subscriptionId2);

        // When
        assertAll(
            () -> {
                var messages = messageFactory.createMessages(room, subscriptions, event, payload);

                assertThat(messages.get(0).getHeaders().getTimeToLive(), equalTo(12L));
                assertThat(messages.get(0).getHeaders().getDeliveryDelay(), equalTo(0L));
                assertThat(messages.get(0).getProperties().getEventId(), equalTo(eventId));
                assertThat(messages.get(0).getProperties().getRoomId(), equalTo(roomId));
                assertThat(messages.get(0).getProperties().getEventTypeId(), equalTo(eventTypeId));
                assertThat(messages.get(0).getProperties().getEventTypeVersionId(), equalTo(eventTypeVersionId));
                assertThat(messages.get(0).getProperties().getPublisherId(), equalTo(publisherId));
                assertThat(messages.get(0).getProperties().getSubscriptionId(), equalTo(subscriptionId1));
                assertThat(messages.get(0).getProperties().getCorrelationId(), equalTo(correlationId));
                assertThat(messages.get(0).getDestination(), equalTo(destination1));
                assertThat(messages.get(0).getHeaders().getDeliveryDelay(), equalTo(0L));

                assertThat(messages.get(1).getHeaders().getTimeToLive(), equalTo(12L));
                assertThat(messages.get(1).getHeaders().getDeliveryDelay(), equalTo(0L));
                assertThat(messages.get(1).getProperties().getEventId(), equalTo(eventId));
                assertThat(messages.get(1).getProperties().getRoomId(), equalTo(roomId));
                assertThat(messages.get(1).getProperties().getEventTypeId(), equalTo(eventTypeId));
                assertThat(messages.get(1).getProperties().getEventTypeVersionId(), equalTo(eventTypeVersionId));
                assertThat(messages.get(1).getProperties().getPublisherId(), equalTo(publisherId));
                assertThat(messages.get(1).getProperties().getSubscriptionId(), equalTo(subscriptionId2));
                assertThat(messages.get(1).getProperties().getCorrelationId(), equalTo(correlationId));
                assertThat(messages.get(1).getDestination(), equalTo(destination2));
                assertThat(messages.get(1).getHeaders().getDeliveryDelay(), equalTo(0L));
            }
        );

    }
}
