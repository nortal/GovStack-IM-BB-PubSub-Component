package com.govstack.information_mediator.pubsub.messaging.web.service;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.EventDeliveryException;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters.builder;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class EventsDeliveryGatewayTest {

    private static MockWebServer subscriptionServer;
    private static final JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
    private static final EventsDeliveryGateway eventDeliveryGateway = new EventsDeliveryGateway();

    @BeforeAll
    static void setup() throws IOException {
        subscriptionServer = new MockWebServer();
        subscriptionServer.start(8181);
        eventDeliveryGateway.xRoadSecurityServerBaseUri = "http://localhost:8181";
        eventDeliveryGateway.xRoadSecurityServerProtocolVersion = "r1";
        eventDeliveryGateway.xRoadClientHeaderValue = "XROAD/MEMBER/CLASS/SUBSYSTEM";
    }

    @AfterAll
    static void tearDown() throws IOException {
        subscriptionServer.shutdown();
    }

    @Test
    void shouldDeliverEventPayloadToSubscription() {
        // Given
        subscriptionServer.enqueue(new MockResponse().setResponseCode(200));
        String pushUrl = "http://localhost:" + subscriptionServer.getPort();
        var subscription = Subscription.builder().parameters(builder().pushUrl(pushUrl).build()).build();
        var payload = jsonService.readTree("{\"hello\": \"world\"}");

        // When
        assertThatNoException().isThrownBy(() -> eventDeliveryGateway.deliverEventPayload(subscription, payload));
    }

    @Test
    void shouldThrowEventDeliveryExceptionWhenDestinationTimeOut() {
        // Given
        subscriptionServer.enqueue(new MockResponse().setResponseCode(408));
        String pushUrl = "http://localhost:" + subscriptionServer.getPort();
        var subscription = Subscription.builder().parameters(builder().pushUrl(pushUrl).build()).build();
        var payload = jsonService.readTree("{\"hello\": \"world\"}");

        // When
        assertThatThrownBy(() -> eventDeliveryGateway.deliverEventPayload(subscription, payload))
            .isInstanceOf(EventDeliveryException.class)
            .hasMessage("Error occurred when delivering event to client, reason: Client responded with status 408");
    }
}
