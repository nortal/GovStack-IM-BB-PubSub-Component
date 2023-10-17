package com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.EventMessage;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Parameters;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PullEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.usecase.pulling.PullEventMessagesUseCase.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PullEventMessageServiceTest {
    @Spy
    private JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;
    @Mock
    private PullEventMessagesPort pullEventMessagesPort;
    @Mock
    private EventsPort eventsPort;
    @Mock
    private PublishedMessagesPort publishedMessagesPort;
    @InjectMocks
    private PullEventMessageService pullEventMessageService;

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void shouldPullEvent(boolean hasAnother) {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var payload = "{\"hello\":\"world\"}";
        var event = EventMessage.builder()
            .eventId(eventId)
            .subscriptionId(subscriptionId)
            .payload(payload)
            .hasAnother(hasAnother)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(pullEventMessagesPort.pullEventMessage(subscription)).thenReturn(Optional.of(event));
        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(false);

        // When
        assertAll(
            () -> {
                var response = pullEventMessageService.execute(request);
                assertThat(response.hasAnother(), equalTo(hasAnother));
                assertThat(jsonService.writeValueAsString(response.payload()), equalTo(payload));
            },
            () -> verify(publishedMessagesPort).markEventIsDelivered(eventId, subscriptionId)
        );
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void shouldNotRedeliverSameEventTwice(boolean hasAnother) {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var payload = "{\"hello\":\"world\"}";
        var event = EventMessage.builder()
            .eventId(eventId)
            .subscriptionId(subscriptionId)
            .payload(payload)
            .hasAnother(hasAnother)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(pullEventMessagesPort.pullEventMessage(subscription)).thenReturn(Optional.of(event));
        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(true);
        when(publishedMessagesPort.isEventDelivered(eventId, subscriptionId)).thenReturn(true);

        // When
        assertAll(
            () -> {
                var response = pullEventMessageService.execute(request);
                assertThat(response.hasAnother(), equalTo(hasAnother));
                assertNull(response.payload());
            },
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void shouldNotDeliverNotPublishedEvent(boolean hasAnother) {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var payload = "{\"hello\":\"world\"}";
        var event = EventMessage.builder()
            .eventId(eventId)
            .subscriptionId(subscriptionId)
            .payload(payload)
            .hasAnother(hasAnother)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(pullEventMessagesPort.pullEventMessage(subscription)).thenReturn(Optional.of(event));
        when(eventsPort.containsEvent(eventId)).thenReturn(true);
        when(publishedMessagesPort.isEventPublished(eventId, subscriptionId)).thenReturn(false);

        // When
        assertAll(
            () -> {
                var response = pullEventMessageService.execute(request);
                assertThat(response.hasAnother(), equalTo(hasAnother));
                assertNull(response.payload());
            },
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @ParameterizedTest
    @CsvSource({"true", "false"})
    void shouldNotDeliverEventThatIsNotSavedInDB(boolean hasAnother) {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var payload = "{\"hello\":\"world\"}";
        var event = EventMessage.builder()
            .eventId(eventId)
            .subscriptionId(subscriptionId)
            .payload(payload)
            .hasAnother(hasAnother)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(pullEventMessagesPort.pullEventMessage(subscription)).thenReturn(Optional.of(event));
        when(eventsPort.containsEvent(eventId)).thenReturn(false);

        // When
        assertAll(
            () -> {
                var response = pullEventMessageService.execute(request);
                assertThat(response.hasAnother(), equalTo(hasAnother));
                assertNull(response.payload());
            },
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldReturnEmptyResponseWhenThereAreNoEvents() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));
        when(pullEventMessagesPort.pullEventMessage(subscription)).thenReturn(Optional.empty());

        // When
        assertAll(
            () -> {
                var response = pullEventMessageService.execute(request);
                assertFalse(response.hasAnother());
                assertNull(response.payload());
            },
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsNotActive() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(PENDING)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsPush() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(roomId)
            .parameters(Parameters.builder().method(PUSH).build())
            .status(ACTIVE)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsInDifferentRoom() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier(subscriptionIdentifier)
            .roomId(UUIDGenerator.randomUUID())
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIdentifierIsDifferent() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var subscription = Subscription.builder()
            .id(subscriptionId)
            .identifier("another-identifier")
            .roomId(roomId)
            .parameters(Parameters.builder().method(PULL).build())
            .status(ACTIVE)
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.of(subscription));

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsNotFound() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.of(room));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId)).thenReturn(Optional.empty());

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionRoomIsNotFound() {
        // Given
        var roomIdentifier = "PatientPortal";
        var subscriptionIdentifier = "EE/SUBS/1234";
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = new Request(roomIdentifier, subscriptionIdentifier, subscriptionId);

        when(fetchRoomsPort.fetchRoomByIdentifier(roomIdentifier)).thenReturn(Optional.empty());

        // When
        assertAll(
            () -> assertThatThrownBy(() -> pullEventMessageService.execute(request))
                .isInstanceOf(RoomNotFoundException.class),
            () -> verify(fetchSubscriptionsPort, times(0)).fetchSubscription(any()),
            () -> verify(pullEventMessagesPort, times(0)).pullEventMessage(any()),
            () -> verify(eventsPort, times(0)).containsEvent(any()),
            () -> verify(publishedMessagesPort, times(0)).isEventPublished(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).isEventDelivered(any(), any()),
            () -> verify(publishedMessagesPort, times(0)).markEventIsDelivered(any(), any())
        );
    }
}
