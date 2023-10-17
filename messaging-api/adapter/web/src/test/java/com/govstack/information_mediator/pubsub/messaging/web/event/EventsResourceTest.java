package com.govstack.information_mediator.pubsub.messaging.web.event;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling.PullEventMessagesUseCase;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.publishing.PublishEventsUseCase;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventsResourceTest {

    private final JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());

    @Mock
    private PullEventMessagesUseCase pullEventMessagesUseCase;

    @Mock
    private PublishEventsUseCase publishEventsUseCase;

    @InjectMocks
    private EventsResource eventsResource;

    @Test
    void testPullEventMessageWhenHasAnotherEvent() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var payload = jsonService.readTree("{\"hello\":\"world\"}");
        when(pullEventMessagesUseCase.execute(any(PullEventMessagesUseCase.Request.class)))
            .thenReturn(new PullEventMessagesUseCase.Response(payload, true));

        // Then
        assertAll(
            () -> {
                var response = eventsResource.pullEventMessage("EE/BUSINESS/123456789", "PatientPortal", subscriptionId);
                assertThat(response)
                    .matches(result -> result.getStatus() == Response.Status.OK.getStatusCode())
                    .matches(result -> result.getEntity().equals(payload))
                    .matches(result -> result.getHeaderString("Another-Event-In-Queue").equals("true"));
                response.close();
            },
            () -> {
                var captor = ArgumentCaptor.forClass(PullEventMessagesUseCase.Request.class);
                verify(pullEventMessagesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request)
                    .matches(result -> result.roomIdentifier().equals("PatientPortal"))
                    .matches(result -> result.subscriptionIdentifier().equals("EE/BUSINESS/123456789"))
                    .matches(result -> result.subscriptionId().equals(subscriptionId));
            }
        );
    }

    @Test
    void testPullEventMessageWhenDoesNotHaveAnotherEvent() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var payload = jsonService.readTree("{\"hello\":\"world\"}");
        when(pullEventMessagesUseCase.execute(any(PullEventMessagesUseCase.Request.class)))
            .thenReturn(new PullEventMessagesUseCase.Response(payload, false));

        // Then
        assertAll(
            () -> {
                var response = eventsResource.pullEventMessage("EE/BUSINESS/123456789", "PatientPortal", subscriptionId);
                assertThat(response)
                    .matches(result -> result.getStatus() == Response.Status.OK.getStatusCode())
                    .matches(result -> result.getEntity().equals(payload))
                    .matches(result -> result.getHeaderString("Another-Event-In-Queue").equals("false"));
                response.close();
            },
            () -> {
                var captor = ArgumentCaptor.forClass(PullEventMessagesUseCase.Request.class);
                verify(pullEventMessagesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request)
                    .matches(result -> result.roomIdentifier().equals("PatientPortal"))
                    .matches(result -> result.subscriptionIdentifier().equals("EE/BUSINESS/123456789"))
                    .matches(result -> result.subscriptionId().equals(subscriptionId));
            }
        );
    }

    @Test
    void testPullEventMessageWhenDoesNotHavePayloadButHasAnotherEvent() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        when(pullEventMessagesUseCase.execute(any(PullEventMessagesUseCase.Request.class)))
            .thenReturn(new PullEventMessagesUseCase.Response(null, true));

        // Then
        assertAll(
            () -> {
                var response = eventsResource.pullEventMessage("EE/BUSINESS/123456789", "PatientPortal", subscriptionId);
                assertThat(response)
                    .matches(result -> result.getStatus() == Response.Status.NO_CONTENT.getStatusCode())
                    .matches(result -> result.getEntity() == null)
                    .matches(result -> result.getHeaderString("Another-Event-In-Queue").equals("true"));
                response.close();
            },
            () -> {
                var captor = ArgumentCaptor.forClass(PullEventMessagesUseCase.Request.class);
                verify(pullEventMessagesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request)
                    .matches(result -> result.roomIdentifier().equals("PatientPortal"))
                    .matches(result -> result.subscriptionIdentifier().equals("EE/BUSINESS/123456789"))
                    .matches(result -> result.subscriptionId().equals(subscriptionId));
            }
        );
    }

    @Test
    void testPullEventMessageWhenDoesNotHavePullEvent() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        when(pullEventMessagesUseCase.execute(any(PullEventMessagesUseCase.Request.class)))
            .thenReturn(PullEventMessagesUseCase.Response.empty());

        // Then
        assertAll(
            () -> {
                var response = eventsResource.pullEventMessage("EE/BUSINESS/123456789", "PatientPortal", subscriptionId);
                assertThat(response)
                    .matches(result -> result.getStatus() == Response.Status.NOT_FOUND.getStatusCode())
                    .matches(result -> result.getEntity() == null)
                    .matches(result -> result.getHeaderString("Another-Event-In-Queue") == null);
                response.close();
            },
            () -> {
                var captor = ArgumentCaptor.forClass(PullEventMessagesUseCase.Request.class);
                verify(pullEventMessagesUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request)
                    .matches(result -> result.roomIdentifier().equals("PatientPortal"))
                    .matches(result -> result.subscriptionIdentifier().equals("EE/BUSINESS/123456789"))
                    .matches(result -> result.subscriptionId().equals(subscriptionId));
            }
        );
    }

    @Test
    void testPublishEventToRoom() {
        // Given
        var content = new ObjectNode(JsonNodeFactory.instance)
            .put("name", "Bruce Wayne")
            .put("secret", "Batman");

        var dto = new PublishEventDTO();
        dto.setContent(content);
        dto.setEventType("newPatient");

        var eventId = UUIDGenerator.randomUUID();
        when(publishEventsUseCase.execute(any())).thenReturn(new PublishEventsUseCase.Response(eventId));

        // Then
        assertAll(
            () -> {
                var response = eventsResource.publishEventToRoom("EE/BUSINESS/123456789", "PatientPortal", dto);
                assertThat(response)
                    .matches(result -> result.getStatus() == Response.Status.OK.getStatusCode())
                    .matches(result -> ((PublishEventResponseDTO) result.getEntity()).getEventId().equals(eventId));
                response.close();
            },
            () -> {
                var argument = ArgumentCaptor.forClass(PublishEventsUseCase.Request.class);
                verify(publishEventsUseCase).execute(argument.capture());

                assertThat(argument.getValue())
                    .matches(result -> result.getPublisher().getIdentifier().equals("EE/BUSINESS/123456789"))
                    .matches(result -> result.getRoom().getIdentifier().equals("PatientPortal"))
                    .matches(request -> request.getEventType().getIdentifier().equals("newPatient"))
                    .matches(result -> result.getPayload().equals(content));
            }
        );
    }
}
