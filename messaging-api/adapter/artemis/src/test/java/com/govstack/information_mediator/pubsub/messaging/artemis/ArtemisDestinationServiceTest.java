package com.govstack.information_mediator.pubsub.messaging.artemis;

import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters;
import org.junit.jupiter.api.Test;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

class ArtemisDestinationServiceTest {

    @Test
    void shouldResolveDestinationForPushSubscription() {
        // Given
        var subscription = Subscription.builder().parameters(Parameters.builder().method(PUSH).build()).build();

        var service = new ArtemisDestinationService();
        service.pushEventsQueue = "pubsub-push-events";

        // When
        assertThat(service.resolveDestination(subscription), equalTo("pubsub-push-events"));
    }

    @Test
    void shouldResolveDestinationForPullSubscription() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var subscription = Subscription.builder().id(subscriptionId).parameters(Parameters.builder().method(PULL).build()).build();

        var destination = "pubsub-pull-events::subscription-%s".formatted(subscriptionId);

        var service = new ArtemisDestinationService();
        service.pullEventsQueueTemplate = "pubsub-pull-events::subscription-%s";

        // When
        assertThat(service.resolveDestination(subscription), equalTo(destination));
    }
}
