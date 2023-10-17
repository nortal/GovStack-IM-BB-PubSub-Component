package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessage.Headers;
import com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessage.Properties;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.CORRELATION_ID_PREFIX;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.DELIVERY_ATTEMPT;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.EVENT_TYPE_VERSION_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.PUBLISHER_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.ROOM_ID;
import static com.govstack.information_mediator.pubsub.messaging.artemis.ArtemisMessageProperty.SUBSCRIPTION_ID;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class ArtemisProducer {

    private final ConnectionFactory connectionFactory;

    public void sendMessage(ArtemisMessage message) {
        try (var connection = openConnection(connectionFactory);
             var session = createSession(connection)) {
            sendMessage(session, message);
        } catch (JMSException e) {
            throw new InternalErrorException(
                "A JMSException occurred when sending a message, reason: " + e.getMessage(), e);
        }
    }

    public void sendMessages(Collection<ArtemisMessage> messages) {
        try (var connection = openConnection(connectionFactory);
             var session = createSession(connection)) {

            for (var message : messages) {
                sendMessage(session, message);
            }
        } catch (JMSException e) {
            throw new InternalErrorException(
                "A JMSException occurred when sending messages, reason: " + e.getMessage(), e);
        }
    }

    private Connection openConnection(ConnectionFactory connectionFactory) throws JMSException {
        var connection = connectionFactory.createConnection();
        connection.start();
        return connection;
    }

    private Session createSession(Connection connection) throws JMSException {
        return connection.createSession();
    }

    private void sendMessage(Session session, ArtemisMessage artemisMessage) throws JMSException {
        var message = createMessage(session, artemisMessage.getPayload(), artemisMessage.getProperties());
        var producer = createProducer(session, artemisMessage.getDestination(), artemisMessage.getHeaders());
        producer.send(message);
    }

    private MessageProducer createProducer(Session session, String destination, Headers headers) throws JMSException {
        var producer = session.createProducer(session.createQueue(destination));
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);
        producer.setDeliveryDelay(headers.getDeliveryDelay());
        producer.setTimeToLive(headers.getTimeToLive());
        return producer;
    }

    private TextMessage createMessage(Session session, String payload, Properties properties) throws JMSException {
        TextMessage message = session.createTextMessage();
        message.setText(payload);
        message.setJMSCorrelationID(CORRELATION_ID_PREFIX + properties.getCorrelationId());
        message.setStringProperty(EVENT_ID, properties.getEventId().toString());
        message.setStringProperty(ROOM_ID, properties.getRoomId().toString());
        message.setStringProperty(EVENT_TYPE_ID, properties.getEventTypeId().toString());
        message.setStringProperty(EVENT_TYPE_VERSION_ID, properties.getEventTypeVersionId().toString());
        message.setStringProperty(PUBLISHER_ID, properties.getPublisherId().toString());
        message.setStringProperty(SUBSCRIPTION_ID, properties.getSubscriptionId().toString());
        message.setIntProperty(DELIVERY_ATTEMPT, properties.getDeliveryAttempt());
        return message;
    }
}
