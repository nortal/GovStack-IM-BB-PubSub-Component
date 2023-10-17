package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.Optional;

@ApplicationScoped
@RequiredArgsConstructor
class ArtemisEBRMessageFactory {

    @ConfigProperty(name = "artemis.queue.pubsub-push-events")
    String pushEventsQueue;

    Optional<ArtemisMessage> createMessage(Room room, Subscription subscription, PushEventMessage pushEvent) {
        var ebrCalculator = EBRCalculator.from(room, subscription);
        var deliveryDelay = ebrCalculator.calculateDeliveryDelay(pushEvent.getDeliveryAttempt());
        int nextDeliveryAttempt = pushEvent.getDeliveryAttempt() + 1;
        if (ebrCalculator.exceedsMaximumDeliveryAttempts(nextDeliveryAttempt)) {
            return Optional.empty();
        }
        return Optional.of(createMessage(pushEvent, nextDeliveryAttempt, deliveryDelay));
    }

    private ArtemisMessage createMessage(PushEventMessage eventMessage, Integer deliveryAttempt, Long deliveryDelay) {
        return ArtemisMessage.builder()
            .headers(ArtemisMessage.Headers.builder()
                .deliveryDelay(deliveryDelay)
                .timeToLive(eventMessage.getTimeToLive())
                .build())
            .properties(ArtemisMessage.Properties.builder()
                .eventId(eventMessage.getEventId())
                .roomId(eventMessage.getRoomId())
                .eventTypeId(eventMessage.getEventTypeId())
                .eventTypeVersionId(eventMessage.getEventTypeVersionId())
                .publisherId(eventMessage.getPublisherId())
                .subscriptionId(eventMessage.getSubscriptionId())
                .correlationId(eventMessage.getCorrelationId())
                .deliveryAttempt(deliveryAttempt)
                .build())
            .destination(pushEventsQueue)
            .payload(eventMessage.getPayload())
            .build();
    }

    record EBRCalculator(Integer deliveryAttempts, Integer deliveryDelay, Double deliveryDelayMultiplier){

        static EBRCalculator from(Room room, Subscription subscription) {
            var deliveryAttempts = Optional.ofNullable(subscription.getParameters().getDeliveryAttempts())
                .orElse(room.getConfiguration().getDeliveryAttempts());

            var deliveryDelay = Optional.ofNullable(subscription.getParameters().getDeliveryDelay())
                .orElse(room.getConfiguration().getDeliveryDelay());

            var deliveryDelayMultiplier = Optional.ofNullable(subscription.getParameters().getDeliveryDelayMultiplier())
                .orElse(room.getConfiguration().getDeliveryDelayMultiplier());

            return new EBRCalculator(deliveryAttempts, deliveryDelay, deliveryDelayMultiplier);
        }

        boolean exceedsMaximumDeliveryAttempts(Integer deliveryAttempt) {
            return deliveryAttempt > deliveryAttempts;
        }

        Long calculateDeliveryDelay(Integer retryNumber) {
            return Double.valueOf(deliveryDelay * Math.pow(deliveryDelayMultiplier, retryNumber)).longValue();
        }
    }
}
