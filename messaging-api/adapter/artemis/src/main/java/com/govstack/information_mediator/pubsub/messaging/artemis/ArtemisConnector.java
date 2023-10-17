package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing.PushEventMessagesUseCase;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing.PushEventMessagesUseCase.Request;
import io.smallrye.common.annotation.Blocking;
import io.smallrye.reactive.messaging.amqp.AmqpMessage;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.reactive.messaging.Incoming;

import java.util.UUID;
import java.util.concurrent.CompletionStage;

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
class ArtemisConnector {

    private final PushEventMessagesUseCase pushEventMessagesUseCase;

    @Blocking
    @Transactional
    @Incoming("pubsub-push-events")
    CompletionStage<Void> onMessage(AmqpMessage<String> message) {
        var request = new Request(maptToPushEventMessage(message));
        pushEventMessagesUseCase.execute(request);
        return message.ack();
    }

    private static PushEventMessage maptToPushEventMessage(AmqpMessage<String> message) {
        var properties = message.getApplicationProperties();
        return PushEventMessage.builder()
            .eventId(getUUIDProperty(properties, EVENT_ID))
            .roomId(getUUIDProperty(properties, ROOM_ID))
            .eventTypeId(getUUIDProperty(properties, EVENT_TYPE_ID))
            .eventTypeVersionId(getUUIDProperty(properties, EVENT_TYPE_VERSION_ID))
            .publisherId(getUUIDProperty(properties, PUBLISHER_ID))
            .subscriptionId(getUUIDProperty(properties, SUBSCRIPTION_ID))
            .deliveryAttempt(getIntegerProperty(properties, DELIVERY_ATTEMPT))
            .correlationId(getCorrelationId(message))
            .timeToLive(message.getExpiryTime())
            .payload(message.getPayload())
            .build();
    }

    private static UUID getUUIDProperty(JsonObject properties, String value) {
        return UUID.fromString(properties.getString(value));
    }

    private static Integer getIntegerProperty(JsonObject properties, String value) {
        return properties.getInteger(value);
    }

    private static String getCorrelationId(AmqpMessage<?> message) {
        return message.getCorrelationId().toString().replaceFirst(CORRELATION_ID_PREFIX, EMPTY_STRING);
    }
}
