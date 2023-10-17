package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Room.Configuration;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters;
import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ArtemisEBRMessageFactoryTest {

    @Test
    void shouldCreateArtemisMessage() {
        // Given
        var factory = new ArtemisEBRMessageFactory();
        factory.pushEventsQueue = "pubsub-push-events";


        var eventId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var publisherId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var deliveryAttempt = 1;
        var timeToLive = 9999L;
        var correlationId = UUIDGenerator.randomUUID().toString();
        var payload = "Payload Text";

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .eventTypeId(eventTypeId)
            .eventTypeVersionId(eventTypeVersionId)
            .publisherId(publisherId)
            .subscriptionId(subscriptionId)
            .deliveryAttempt(deliveryAttempt)
            .timeToLive(timeToLive)
            .correlationId(correlationId)
            .payload(payload)
            .build();

        var room = Room.builder().id(roomId)
            .configuration(Configuration.builder()
                .deliveryAttempts(0)
                .deliveryDelay(10)
                .deliveryDelayMultiplier(1.01)
                .build())
            .build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .parameters(Parameters.builder()
                .deliveryAttempts(3)
                .deliveryDelay(1000)
                .deliveryDelayMultiplier(1.5)
                .build())
            .build();

        var nextDeliveryAttempt = deliveryAttempt + 1;
        var calculatedDeliveryDelay = 1500L;

        // When
        Assertions.assertAll(
            () -> {
                var artemisMessage = factory.createMessage(room, subscription, message);
                assertTrue(artemisMessage.isPresent());

                assertThat(artemisMessage.get().getHeaders().getDeliveryDelay(), equalTo(calculatedDeliveryDelay));
                assertThat(artemisMessage.get().getHeaders().getTimeToLive(), equalTo(timeToLive));

                assertThat(artemisMessage.get().getProperties().getEventId(), equalTo(eventId));
                assertThat(artemisMessage.get().getProperties().getRoomId(), equalTo(roomId));
                assertThat(artemisMessage.get().getProperties().getEventTypeId(), equalTo(eventTypeId));
                assertThat(artemisMessage.get().getProperties().getEventTypeVersionId(), equalTo(eventTypeVersionId));
                assertThat(artemisMessage.get().getProperties().getPublisherId(), equalTo(publisherId));
                assertThat(artemisMessage.get().getProperties().getSubscriptionId(), equalTo(subscriptionId));
                assertThat(artemisMessage.get().getProperties().getCorrelationId(), equalTo(correlationId));
                assertThat(artemisMessage.get().getProperties().getDeliveryAttempt(), equalTo(nextDeliveryAttempt));
                assertThat(artemisMessage.get().getDestination(), equalTo("pubsub-push-events"));
                assertThat(artemisMessage.get().getPayload(), equalTo(payload));
            }
        );
    }

    @ParameterizedTest
    @CsvSource({"1,true", "2,true", "3,false", "4,false"})
    void testDeliveryAttemptDecision(int deliveryAttempt, boolean isMessageCreated) {
        // Given
        var factory = new ArtemisEBRMessageFactory();
        factory.pushEventsQueue = "pubsub-push-events";

        var message = PushEventMessage.builder().deliveryAttempt(deliveryAttempt).build();

        var room = Room.builder()
            .configuration(Configuration.builder()
                .deliveryAttempts(0)
                .deliveryDelay(10)
                .deliveryDelayMultiplier(1.01)
                .build())
            .build();

        var subscription = Subscription.builder()
            .parameters(Parameters.builder()
                .deliveryAttempts(3)
                .deliveryDelay(1000)
                .deliveryDelayMultiplier(1.5)
                .build())
            .build();


        Assertions.assertAll(
            () -> {
                var artemisMessage = factory.createMessage(room, subscription, message);
                assertThat(artemisMessage.isPresent(), equalTo(isMessageCreated));
            }
        );
    }

    @Test
    void shouldCorrectlyUseDeliveryProperties() {
        // Then
        assertAll(
            () -> {
                var artemisEbrResolver = ArtemisEBRMessageFactory.EBRCalculator.from(
                    createRoom(0, 10, 1.01),
                    createSubscription(3, 1000, 1.5));

                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(1), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(2), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(3), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(4), equalTo(true));

                assertThat(artemisEbrResolver.calculateDeliveryDelay(2), equalTo(2250L));
            },
            () -> {
                var artemisEbrResolver = ArtemisEBRMessageFactory.EBRCalculator.from(
                    createRoom(2, 10, 1.01),
                    createSubscription(null, 1000, 1.5));

                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(1), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(2), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(3), equalTo(true));

                assertThat(artemisEbrResolver.calculateDeliveryDelay(2), equalTo(2250L));
            },
            () -> {
                var artemisEbrResolver = ArtemisEBRMessageFactory.EBRCalculator.from(
                    createRoom(2, 10, 1.01),
                    createSubscription(null, null, 1.5));

                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(1), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(2), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(3), equalTo(true));

                assertThat(artemisEbrResolver.calculateDeliveryDelay(2), equalTo(22L));
            },
            () -> {
                var artemisEbrResolver = ArtemisEBRMessageFactory.EBRCalculator.from(
                    createRoom(2, 10, 1.01),
                    createSubscription(null, null, null));

                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(1), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(2), equalTo(false));
                assertThat(artemisEbrResolver.exceedsMaximumDeliveryAttempts(3), equalTo(true));

                assertThat(artemisEbrResolver.calculateDeliveryDelay(2), equalTo(10L));
            }
        );
    }

    private Subscription createSubscription(Integer deliveryAttempts, Integer deliveryDelay, Double multiplier) {
        return Subscription.builder().parameters(Parameters.builder().deliveryAttempts(deliveryAttempts).deliveryDelay(deliveryDelay).deliveryDelayMultiplier(multiplier).build()).build();
    }

    private Room createRoom(Integer deliveryAttempts, Integer deliveryDelay, Double multiplier) {
        return Room.builder().configuration(Configuration.builder().deliveryAttempts(deliveryAttempts).deliveryDelay(deliveryDelay).deliveryDelayMultiplier(multiplier).build()).build();
    }
}
