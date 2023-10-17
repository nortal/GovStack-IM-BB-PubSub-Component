package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing.PushEventMessagesUseCase;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing.PushEventMessagesUseCase.Request;
import io.smallrye.reactive.messaging.amqp.AmqpMessage;
import io.vertx.core.json.JsonObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.DELIVERY_ATTEMPT;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_VERSION_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.PUBLISHER_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.ROOM_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.SUBSCRIPTION_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtemisConnectorTest {
    @Mock
    private PushEventMessagesUseCase pushEventMessagesUseCase;
    @Mock
    private AmqpMessage<String> message;
    @InjectMocks
    private ArtemisConnector artemisConnector;

    @Test
    void shouldProcessIncomingMessages() {
        // Given
        var eventId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var publisherId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var deliveryAttempt = 3;
        var correlationId = UUIDGenerator.randomUUID();
        var timeToLive = 10002l;
        var payload = "Simple text message";

        var ack = CompletableFuture.runAsync(() -> { });

        var properties = mock(JsonObject.class);
        when(message.getApplicationProperties()).thenReturn(properties);
        when(properties.getString(EVENT_ID)).thenReturn(eventId.toString());
        when(properties.getString(ROOM_ID)).thenReturn(roomId.toString());
        when(properties.getString(EVENT_TYPE_ID)).thenReturn(eventTypeId.toString());
        when(properties.getString(EVENT_TYPE_VERSION_ID)).thenReturn(eventTypeVersionId.toString());
        when(properties.getString(PUBLISHER_ID)).thenReturn(publisherId.toString());
        when(properties.getString(SUBSCRIPTION_ID)).thenReturn(subscriptionId.toString());
        when(properties.getInteger(DELIVERY_ATTEMPT)).thenReturn(deliveryAttempt);

        when(message.getCorrelationId()).thenReturn("ID:" + correlationId);
        when(message.getExpiryTime()).thenReturn(timeToLive);
        when(message.getPayload()).thenReturn(payload);

        when(message.ack()).thenReturn(ack);

        // When
        assertAll(
            () -> assertThat(artemisConnector.onMessage(message), equalTo(ack)),
            () -> {
                var captor = ArgumentCaptor.forClass(Request.class);
                verify(pushEventMessagesUseCase).execute(captor.capture());
                var event = captor.getValue().eventMessage();
                assertThat(event.getEventId(), equalTo(eventId));
                assertThat(event.getRoomId(), equalTo(roomId));
                assertThat(event.getEventTypeId(), equalTo(eventTypeId));
                assertThat(event.getEventTypeVersionId(), equalTo(eventTypeVersionId));
                assertThat(event.getPublisherId(), equalTo(publisherId));
                assertThat(event.getSubscriptionId(), equalTo(subscriptionId));
                assertThat(event.getDeliveryAttempt(), equalTo(deliveryAttempt));
                assertThat(event.getCorrelationId(), equalTo(correlationId.toString()));
                assertThat(event.getTimeToLive(), equalTo(timeToLive));
                assertThat(event.getPayload(), equalTo(payload));
            }
        );
    }
}
