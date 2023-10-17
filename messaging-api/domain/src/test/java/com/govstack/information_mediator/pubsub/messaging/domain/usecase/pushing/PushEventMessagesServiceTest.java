package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing;

import com.fasterxml.jackson.databind.JsonNode;
import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.EventDeliveryException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters;
import com.govstack.information_mediator.pubsub.messaging.domain.entity.PushEventMessage;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsDeliveryPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pushing.PushEventMessagesUseCase.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PushEventMessagesServiceTest {
    @Spy
    private JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());

    @Mock
    private EventsPort eventsPort;
    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private PublishedMessagesPort publishedMessagesPort;
    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;
    @Mock
    private EventsDeliveryPort eventsDeliveryPort;
    @Mock
    private PublishEventMessagesPort publishEventMessagesPort;

    @InjectMocks
    private PushEventMessagesService pushEventMessagesService;

    @Test
    void shouldPushEventToSubscriber() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .roomId(roomId)
            .status(ACTIVE)
            .parameters(Parameters.builder().method(PUSH).build())
            .build();

        var room = Room.builder().id(roomId).build();

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(fetchRoomsPort.fetchRoom(roomId)).thenReturn(Optional.of(room));

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> {
                var payloadCaptor = ArgumentCaptor.forClass(JsonNode.class);
                verify(eventsDeliveryPort).deliverEventPayload(eq(subscription), payloadCaptor.capture());
                assertThat(jsonService.writeValueAsString(payloadCaptor.getValue()), equalTo("{\"hello\":\"world\"}"));
            },
            () -> verify(publishedMessagesPort).markEventIsDelivered(eventId, subscriptionId),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verifyNoInteractions(publishEventMessagesPort)
        );
    }

    @Test
    void shouldRepublishEvent() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .roomId(roomId)
            .status(ACTIVE)
            .parameters(Parameters.builder().method(PUSH).build())
            .build();

        var room = Room.builder().id(roomId).build();

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(fetchRoomsPort.fetchRoom(roomId)).thenReturn(Optional.of(room));
        doThrow(EventDeliveryException.class)
            .when(eventsDeliveryPort)
            .deliverEventPayload(eq(subscription), any(JsonNode.class));

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> {
                var payloadCaptor = ArgumentCaptor.forClass(JsonNode.class);
                verify(eventsDeliveryPort).deliverEventPayload(eq(subscription), payloadCaptor.capture());
                assertThat(jsonService.writeValueAsString(payloadCaptor.getValue()), equalTo("{\"hello\":\"world\"}"));
            },
            () -> verify(publishedMessagesPort).markEventIsPublished(eventId, subscriptionId),
            () -> verify(publishEventMessagesPort).republishEvent(message, subscription, room),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfRoomIsNotActive() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .roomId(roomId)
            .status(ACTIVE)
            .parameters(Parameters.builder().method(PUSH).build())
            .build();

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(fetchRoomsPort.fetchRoom(roomId)).thenReturn(Optional.empty());

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfSubscriptionIsNotActive() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .roomId(roomId)
            .status(PENDING)
            .parameters(Parameters.builder().method(PUSH).build())
            .build();

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfSubscriptionIsNotPush() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .roomId(roomId)
            .status(ACTIVE)
            .parameters(Parameters.builder().method(PULL).build())
            .build();

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfSubscriptionIsNotFound() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.empty());

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfIsDelivered() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(true);

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfIsNotPublishedDelivered() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(false);

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }

    @Test
    void shouldIgnoreMessageIfIsNotSavedInDB() {
        // when
        var eventId = UUIDGenerator.randomUUID();
        var subscriptionId = UUIDGenerator.randomUUID();
        var roomId = UUIDGenerator.randomUUID();

        var message = PushEventMessage.builder()
            .eventId(eventId)
            .roomId(roomId)
            .payload("{\"hello\":\"world\"}")
            .subscriptionId(subscriptionId)
            .build();

        var request = new Request(message);

        when(eventsPort.containsEvent(eventId)).thenReturn(false);

        // then
        assertAll(
            () -> assertThatNoException().isThrownBy(() -> pushEventMessagesService.execute(request)),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsPublished(any(), any()),
            () -> verify(eventsDeliveryPort, times(0)).deliverEventPayload(any(), any())
        );
    }
}
