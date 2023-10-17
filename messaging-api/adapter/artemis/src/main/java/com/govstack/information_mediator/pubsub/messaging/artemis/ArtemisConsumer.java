package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessage.Properties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.CORRELATION_ID_PREFIX;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.DELIVERY_ATTEMPT;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EMPTY_STRING;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_VERSION_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.PUBLISHER_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.ROOM_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.SUBSCRIPTION_ID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class ArtemisConsumer {

    private final ConnectionFactory connectionFactory;

    public boolean hasMoreMessages(String destination) {
        try (var connection = openConnection(connectionFactory);
             var session = connection.createSession()) {
            return hasMoreMessages(session, destination);
        } catch (JMSException e) {
            throw new InternalErrorException(
                "A JMSException occurred when checking for more messages on queue: " + destination, e);
        }
    }

    private Connection openConnection(ConnectionFactory connectionFactory) throws JMSException {
        var connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }

    private boolean hasMoreMessages(Session session, String destination) throws JMSException {
        var browser = session.createBrowser(session.createQueue(destination));
        return browser.getEnumeration().hasMoreElements();
    }

    public Optional<ArtemisMessage> receiveMessage(String destination) {
        try (var connection = openConnection(connectionFactory);
             var session = connection.createSession()) {
            return receiveMessage(session, destination);
        } catch (JMSException e) {
            throw new InternalErrorException(
                "A JMSException occurred when receiving message from queue: " + destination, e);
        }
    }

    private Optional<ArtemisMessage> receiveMessage(Session session, String destination) throws JMSException {
        var consumer = session.createConsumer(session.createQueue(destination));
        var message = consumer.receiveNoWait();
        return toArtemisMessage(message, destination);
    }

    private Optional<ArtemisMessage> toArtemisMessage(Message message, String destination) throws JMSException {
        if (message instanceof TextMessage) {
            var payload = ((TextMessage) message).getText();
            if (payload == null) {
                return Optional.empty();
            }

            var artemisMessage = ArtemisMessage.builder()
                .payload(payload)
                .destination(destination)
                .properties(resolveMessageProperties(message))
                .build();

            return Optional.of(artemisMessage);
        }
        return Optional.empty();
    }

    private Properties resolveMessageProperties(Message message) throws JMSException {
        return Properties.builder()
            .eventId(getUUIDProperty(message, EVENT_ID))
            .roomId(getUUIDProperty(message, ROOM_ID))
            .eventTypeId(getUUIDProperty(message, EVENT_TYPE_ID))
            .eventTypeVersionId(getUUIDProperty(message, EVENT_TYPE_VERSION_ID))
            .publisherId(getUUIDProperty(message, PUBLISHER_ID))
            .subscriptionId(getUUIDProperty(message, SUBSCRIPTION_ID))
            .correlationId(getCorrelationId(message))
            .deliveryAttempt(getIntegerProperty(message, DELIVERY_ATTEMPT))
            .build();
    }

    private UUID getUUIDProperty(Message message, String property) throws JMSException {
        return UUID.fromString(message.getStringProperty(property));
    }

    private String getCorrelationId(Message message) throws JMSException {
        return message.getJMSCorrelationID().replaceFirst(CORRELATION_ID_PREFIX, EMPTY_STRING);
    }

    private Integer getIntegerProperty(Message message, String property) throws JMSException {
        return message.getIntProperty(property);
    }
}
