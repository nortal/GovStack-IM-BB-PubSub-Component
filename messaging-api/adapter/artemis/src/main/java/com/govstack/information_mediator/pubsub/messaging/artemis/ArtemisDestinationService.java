package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;

@ApplicationScoped
class ArtemisDestinationService {

    @ConfigProperty(name = "artemis.queue.pubsub-push-events")
    String pushEventsQueue;

    @ConfigProperty(name = "artemis.queue-template.pubsub-pull-events")
    String pullEventsQueueTemplate;

    String resolveDestination(Subscription subscription) {
        var method = subscription.getParameters().getMethod();
        if (method == PUSH) {
            return pushEventsQueue;
        } else if (method == PULL) {
            return pullEventsQueueTemplate.formatted(subscription.getId());
        } else {
            throw new ApiException("Failed to resolve address::queue destination for subscription: " + subscription.getId());
        }
    }

}
