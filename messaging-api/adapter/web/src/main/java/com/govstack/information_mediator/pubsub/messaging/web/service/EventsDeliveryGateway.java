package com.govstack.information_mediator.pubsub.messaging.web.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.exception.EventDeliveryException;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsDeliveryPort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.ProcessingException;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.net.URI;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

@Slf4j
@ApplicationScoped
@RequiredArgsConstructor
class EventsDeliveryGateway implements EventsDeliveryPort {

    public static final String X_ROAD_CLIENT_HEADER_KEY = "X-Road-Client";

    @Inject
    @ConfigProperty(name = "im-bb.x-road.security-server-uri")
    String xRoadSecurityServerBaseUri;

    @Inject
    @ConfigProperty(name = "im-bb.x-road.security-server-protocol-version")
    String xRoadSecurityServerProtocolVersion;

    @Inject
    @ConfigProperty(name = "im-bb.x-road.client-header-value")
    String xRoadClientHeaderValue;

    public void deliverEventPayload(Subscription subscription, JsonNode payload) {
        try (var client = ClientBuilder.newClient()) {
            var subscriberPushUriPath = subscription.getParameters().getPushUrl();
            URI fullPushUrl = UriBuilder.fromUri(xRoadSecurityServerBaseUri)
                .path(xRoadSecurityServerProtocolVersion)
                .path(subscriberPushUriPath)
                .build();

            var response = client.target(fullPushUrl)
                .request(APPLICATION_JSON)
                .header(X_ROAD_CLIENT_HEADER_KEY, xRoadClientHeaderValue)
                .post(Entity.entity(payload, APPLICATION_JSON));

            var status = response.getStatus();
            close(response);

            if (status != Response.Status.OK.getStatusCode()) {
                throw new EventDeliveryException("Client responded with status " + status);
            }

        } catch (Exception e) {
            throw new EventDeliveryException("Error occurred when delivering event to client, reason: " + e.getMessage(), e);
        }
    }

    private void close(Response response) {
        try {
            response.close();
        } catch (ProcessingException e) {
            if (log.isDebugEnabled()) {
                log.debug("Ignoring error on close response", e);
            }
        }
    }
}
