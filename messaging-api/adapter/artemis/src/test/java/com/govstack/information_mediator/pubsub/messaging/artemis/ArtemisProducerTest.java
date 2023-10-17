package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessage.Headers;
import com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessage.Properties;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Queue;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;

import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.CORRELATION_ID_PREFIX;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.DELIVERY_ATTEMPT;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_VERSION_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.PUBLISHER_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.ROOM_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.SUBSCRIPTION_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtemisProducerTest {

    @Spy
    private MockArtemisClock clock;
    @Mock
    private ConnectionFactory connectionFactory;
    @InjectMocks
    private ArtemisProducer artemisProducer;

    @Test
    void shouldSendMessage() throws JMSException {
        // Given
        var eventId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var publisherId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var correlationId = UUIDGenerator.randomUUID().toString();
        var jmsCorrelationId = CORRELATION_ID_PREFIX + correlationId;
        var deliveryAttempt = 2;
        var deliveryDelay = 1L;
        var timeToLive = 60L;
        var payload = "Test payload";
        var destination = "pubsub-manual::%s".formatted(subscriptionId);

        var artemisMessage = ArtemisMessage.builder()
                .headers(Headers.builder()
                        .deliveryDelay(deliveryDelay)
                        .timeToLive(timeToLive)
                        .build())
                .properties(Properties.builder()
                        .eventId(eventId)
                        .roomId(roomId)
                        .eventTypeId(eventTypeId)
                        .eventTypeVersionId(eventTypeVersionId)
                        .publisherId(publisherId)
                        .subscriptionId(subscriptionId)
                        .correlationId(correlationId)
                        .deliveryAttempt(deliveryAttempt)
                        .build())
                .destination(destination)
                .payload(payload)
                .build();

        var connection = mock(Connection.class);
        when(connectionFactory.createConnection()).thenReturn(connection);

        var session = mock(Session.class);
        when(connection.createSession()).thenReturn(session);

        var publishedAt = clock.instant();
        var message = mock(TextMessage.class);
        when(session.createTextMessage()).thenReturn(message);

        var queue = mock(Queue.class);
        when(session.createQueue(destination)).thenReturn(queue);

        var producer = mock(MessageProducer.class);
        when(session.createProducer(queue)).thenReturn(producer);

        // When
        artemisProducer.sendMessage(artemisMessage);

        // Then
        verify(producer).setDeliveryMode(DeliveryMode.PERSISTENT);
        verify(producer).setDeliveryDelay(deliveryDelay);
        verify(producer).setTimeToLive(timeToLive);

        verify(producer).send(message);

        verify(message).setText(payload);
        verify(message).setJMSCorrelationID(jmsCorrelationId);
        verify(message).setStringProperty(EVENT_ID, eventId.toString());
        verify(message).setStringProperty(ROOM_ID, roomId.toString());
        verify(message).setStringProperty(EVENT_TYPE_ID, eventTypeId.toString());
        verify(message).setStringProperty(EVENT_TYPE_VERSION_ID, eventTypeVersionId.toString());
        verify(message).setStringProperty(PUBLISHER_ID, publisherId.toString());
        verify(message).setStringProperty(SUBSCRIPTION_ID, subscriptionId.toString());
        verify(message).setIntProperty(DELIVERY_ATTEMPT, deliveryAttempt);

        verify(connection).start();
    }

    @Test
    void shouldSendMessages() throws JMSException {
        // Given
        var eventId_1 = UUIDGenerator.randomUUID();
        var roomId_1 = UUIDGenerator.randomUUID();
        var eventTypeId_1 = UUIDGenerator.randomUUID();
        var eventTypeVersionId_1 = UUIDGenerator.randomUUID();
        var publisherId_1 = UUIDGenerator.randomUUID();
        var subscriptionId_1 = UUIDGenerator.randomUUID();
        var correlationId_1 = UUIDGenerator.randomUUID().toString();
        var jmsCorrelationId_1 = CORRELATION_ID_PREFIX + correlationId_1;
        var deliveryAttempt_1 = 2;
        var deliveryDelay_1 = 1L;
        var timeToLive_1 = 60L;
        var payload_1 = "Test payload 1";
        var destination_1 = "pubsub-manual::%s".formatted(subscriptionId_1);

        var eventId_2 = UUIDGenerator.randomUUID();
        var roomId_2 = UUIDGenerator.randomUUID();
        var eventTypeId_2 = UUIDGenerator.randomUUID();
        var eventTypeVersionId_2 = UUIDGenerator.randomUUID();
        var publisherId_2 = UUIDGenerator.randomUUID();
        var subscriptionId_2 = UUIDGenerator.randomUUID();
        var correlationId_2 = UUIDGenerator.randomUUID().toString();
        var jmsCorrelationId_2 = CORRELATION_ID_PREFIX + correlationId_2;
        var deliveryAttempt_2 = 2;
        var deliveryDelay_2 = 1L;
        var timeToLive_2 = 60L;
        var payload_2 = "Test payload 2";
        var destination_2 = "pubsub-manual::%s".formatted(subscriptionId_2);

        var artemisMessage_1 = ArtemisMessage.builder()
                .headers(Headers.builder()
                        .deliveryDelay(deliveryDelay_1)
                        .timeToLive(timeToLive_1)
                        .build())
                .properties(Properties.builder()
                        .eventId(eventId_1)
                        .roomId(roomId_1)
                        .eventTypeId(eventTypeId_1)
                        .eventTypeVersionId(eventTypeVersionId_1)
                        .publisherId(publisherId_1)
                        .subscriptionId(subscriptionId_1)
                        .correlationId(correlationId_1)
                        .deliveryAttempt(deliveryAttempt_1)
                        .build())
                .destination(destination_1)
                .payload(payload_1)
                .build();

        var artemisMessage_2 = ArtemisMessage.builder()
                .headers(Headers.builder()
                        .deliveryDelay(deliveryDelay_2)
                        .timeToLive(timeToLive_2)
                        .build())
                .properties(Properties.builder()
                        .eventId(eventId_2)
                        .roomId(roomId_2)
                        .eventTypeId(eventTypeId_2)
                        .eventTypeVersionId(eventTypeVersionId_2)
                        .publisherId(publisherId_2)
                        .subscriptionId(subscriptionId_2)
                        .correlationId(correlationId_2)
                        .deliveryAttempt(deliveryAttempt_2)
                        .build())
                .destination(destination_2)
                .payload(payload_2)
                .build();

        var artemisMessages = List.of(artemisMessage_1, artemisMessage_2);

        var connection = mock(Connection.class);
        when(connectionFactory.createConnection()).thenReturn(connection);

        var session = mock(Session.class);
        when(connection.createSession()).thenReturn(session);

        clock.setNow(Instant.now());
        var publishedAt_1 = clock.instant();
        var message_1 = mock(TextMessage.class);

        clock.setNow(Instant.now());
        var publishedAt_2 = clock.instant();
        var message_2 = mock(TextMessage.class);


        when(session.createTextMessage()).thenReturn(message_1, message_2);

        var queue_1 = mock(Queue.class);
        when(session.createQueue(destination_1)).thenReturn(queue_1);

        var queue_2 = mock(Queue.class);
        when(session.createQueue(destination_2)).thenReturn(queue_2);


        var producer_1 = mock(MessageProducer.class);
        when(session.createProducer(queue_1)).thenReturn(producer_1);

        var producer_2 = mock(MessageProducer.class);
        when(session.createProducer(queue_2)).thenReturn(producer_2);

        // When
        artemisProducer.sendMessages(artemisMessages);

        // Then
        verify(producer_1).setDeliveryMode(DeliveryMode.PERSISTENT);
        verify(producer_1).setDeliveryDelay(deliveryDelay_1);
        verify(producer_1).setTimeToLive(timeToLive_1);

        verify(producer_1).send(message_1);

        verify(message_1).setText(payload_1);
        verify(message_1).setJMSCorrelationID(jmsCorrelationId_1);
        verify(message_1).setStringProperty(EVENT_ID, eventId_1.toString());
        verify(message_1).setStringProperty(ROOM_ID, roomId_1.toString());
        verify(message_1).setStringProperty(EVENT_TYPE_ID, eventTypeId_1.toString());
        verify(message_1).setStringProperty(EVENT_TYPE_VERSION_ID, eventTypeVersionId_1.toString());
        verify(message_1).setStringProperty(PUBLISHER_ID, publisherId_1.toString());
        verify(message_1).setStringProperty(SUBSCRIPTION_ID, subscriptionId_1.toString());
        verify(message_1).setIntProperty(DELIVERY_ATTEMPT, deliveryAttempt_1);

        verify(producer_2).setDeliveryMode(DeliveryMode.PERSISTENT);
        verify(producer_2).setDeliveryDelay(deliveryDelay_2);
        verify(producer_2).setTimeToLive(timeToLive_2);

        verify(producer_2).send(message_2);

        verify(message_2).setText(payload_2);
        verify(message_2).setJMSCorrelationID(jmsCorrelationId_2);
        verify(message_2).setStringProperty(EVENT_ID, eventId_2.toString());
        verify(message_2).setStringProperty(ROOM_ID, roomId_2.toString());
        verify(message_2).setStringProperty(EVENT_TYPE_ID, eventTypeId_2.toString());
        verify(message_2).setStringProperty(EVENT_TYPE_VERSION_ID, eventTypeVersionId_2.toString());
        verify(message_2).setStringProperty(PUBLISHER_ID, publisherId_2.toString());
        verify(message_2).setStringProperty(SUBSCRIPTION_ID, subscriptionId_2.toString());
        verify(message_2).setIntProperty(DELIVERY_ATTEMPT, deliveryAttempt_2);

        verify(connection).start();
    }

    @Test
    void shouldHandleJMSExceptionWhenSendingMessage() throws JMSException {
        // Given
        var artemisMessage = ArtemisMessage.builder().build();

        var connection = mock(Connection.class);
        when(connectionFactory.createConnection()).thenReturn(connection);

        var session = mock(Session.class);
        when(connection.createSession()).thenReturn(session);

        when(session.createTextMessage()).thenThrow(new JMSException("A wild JMS exception appears!"));

        // When
        assertThatThrownBy(() -> artemisProducer.sendMessage(artemisMessage)).isInstanceOf(InternalErrorException.class);
    }

    @Test
    void shouldHandleJMSExceptionWhenSendingMessages() throws JMSException {
        // Given
        var artemisMessages = List.of(ArtemisMessage.builder().build());

        var connection = mock(Connection.class);
        when(connectionFactory.createConnection()).thenReturn(connection);

        var session = mock(Session.class);
        when(connection.createSession()).thenReturn(session);

        when(session.createTextMessage()).thenThrow(new JMSException("A wild JMS exception appears!"));

        // When
        assertThatThrownBy(() -> artemisProducer.sendMessages(artemisMessages)).isInstanceOf(InternalErrorException.class);
    }
}
