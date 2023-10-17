package com.govstack.information_mediator.pubsub.messaging.domain.usecase.publishing;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory;
import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.PublisherNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.schema.SchemaProvider;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.EventType.Version;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.PublisherIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.messaging.domain.MockClock;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishEventMessagesPort;
import com.govstack.information_mediator.pubsub.messaging.domain.port.PublishedMessagesPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.ACTIVE;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublishEventsServiceTest {
    private static final String DEFAULT_SCHEMA = "{\"type\":\"object\"}";

    @Spy
    private MockClock clock;
    @Mock
    private EventsPort eventsPort;
    @Spy
    private JsonService jsonService = new JsonService(ObjectMapperFactory.getObjectMapper());
    @Mock
    private FetchRoomsPort fetchRoomsPort;
    @Mock
    private FetchPublishersPort fetchPublishersPort;
    @Mock
    private FetchEventTypesPort fetchEventTypesPort;
    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;
    @Mock
    private PublishedMessagesPort publishedMessagesPort;
    @Mock
    private PublishEventMessagesPort publishEventMessagesPort;

    @InjectMocks
    private PublishEventsService publishEventsService;

    @Captor
    private ArgumentCaptor<List<Subscription>> subscriptionsCaptor;

    @Test
    void shouldPublishEvent() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var publisherId = UUIDGenerator.randomUUID();
        var publisher = Publisher.builder().id(publisherId).build();

        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventType = EventType.builder()
            .id(eventTypeId)
            .version(Version.builder()
                .id(eventTypeVersionId)
                .versionNo(10)
                .jsonSchema(new SchemaProvider(DEFAULT_SCHEMA).getSchema())
                .build())
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var eventCreatedAt = Instant.now();
        clock.setNow(eventCreatedAt);

        var subscriptionID1 = UUIDGenerator.randomUUID();
        var subscription1 = Subscription.builder().id(subscriptionID1).status(ACTIVE).build();

        var subscriptionID2 = UUIDGenerator.randomUUID();
        var subscription2 = Subscription.builder().id(subscriptionID2).status(ACTIVE).build();

        var subscriptionID3 = UUIDGenerator.randomUUID();
        var subscription3 = Subscription.builder().id(subscriptionID3).status(PENDING).build();

        var subscriptions = List.of(subscription1, subscription2, subscription3);

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchPublishersPort.fetchPublisher(room, "EE/PUB/123")).thenReturn(Optional.of(publisher));
        when(fetchEventTypesPort.fetchEventType(publisher, eventTypeIdentifier)).thenReturn(Optional.of(eventType));
        when(eventsPort.createEvent(any(Event.class))).thenReturn(eventId);
        when(fetchSubscriptionsPort.fetchSubscriptions(any(RoomID.class), any(EventTypeID.class))).thenReturn(subscriptions);

        // When
        assertAll(
            () -> {
                var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
                var response = publishEventsService.execute(request);
                assertThat(response.eventId(), equalTo(eventId));
            },
            () -> {
                var argumentCaptor = ArgumentCaptor.forClass(UUID.class);
                verify(publishedMessagesPort, times(2)).markEventIsPublished(eq(eventId), argumentCaptor.capture());
                assertThat(argumentCaptor.getAllValues(), contains(subscriptionID1, subscriptionID2));
            },
            () -> {
                var payloadCaptor = ArgumentCaptor.forClass(String.class);
                var eventCaptor = ArgumentCaptor.forClass(Event.class);
                verify(publishEventMessagesPort).publishEvent(eq(room), subscriptionsCaptor.capture(), eventCaptor.capture(), payloadCaptor.capture());

                assertThat(payloadCaptor.getValue(), equalTo("{\"hello\":\"world\"}"));
                assertThat(subscriptionsCaptor.getValue(), contains(subscription1, subscription2));

                var event = eventCaptor.getValue();
                assertThat(event.getId(), equalTo(eventId));
                assertThat(event.getRoomId(), equalTo(roomId));
                assertThat(event.getEventTypeId(), equalTo(eventTypeId));
                assertThat(event.getPublisherId(), equalTo(publisherId));
                assertNotNull(event.getCorrelationId());
                assertNotNull(event.getCreatedAt());
            }
        );
    }

    @Test
    void shouldNotPublishEventWhenSubscriptionListIsEmpty() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var publisherId = UUIDGenerator.randomUUID();
        var publisher = Publisher.builder().id(publisherId).build();

        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventType = EventType.builder()
            .id(eventTypeId)
            .version(Version.builder()
                .id(eventTypeVersionId)
                .versionNo(10)
                .jsonSchema(new SchemaProvider(DEFAULT_SCHEMA).getSchema())
                .build())
            .build();

        var eventId = UUIDGenerator.randomUUID();
        var eventCreatedAt = Instant.now();
        clock.setNow(eventCreatedAt);

        var subscriptionID = UUIDGenerator.randomUUID();
        var subscription = Subscription.builder().id(subscriptionID).status(PENDING).build();

        var subscriptions = List.of(subscription);

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchPublishersPort.fetchPublisher(room, "EE/PUB/123")).thenReturn(Optional.of(publisher));
        when(fetchEventTypesPort.fetchEventType(publisher, eventTypeIdentifier)).thenReturn(Optional.of(eventType));
        when(eventsPort.createEvent(any(Event.class))).thenReturn(eventId);
        when(fetchSubscriptionsPort.fetchSubscriptions(any(RoomID.class), any(EventTypeID.class))).thenReturn(subscriptions);

        // When
        assertAll(
            () -> {
                var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
                var response = publishEventsService.execute(request);
                assertThat(response.eventId(), equalTo(eventId));
            },
            () -> verifyNoInteractions(publishedMessagesPort, publishEventMessagesPort)
        );
    }

    @Test
    void shouldThrowExceptionWhenPayloadIsInvalid() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var publisherId = UUIDGenerator.randomUUID();
        var publisher = Publisher.builder().id(publisherId).build();

        var eventTypeVersionId = UUIDGenerator.randomUUID();
        var eventTypeId = UUIDGenerator.randomUUID();
        var eventType = EventType.builder()
            .id(eventTypeId)
            .version(Version.builder()
                .id(eventTypeVersionId)
                .versionNo(10)
                .jsonSchema(new SchemaProvider("{\"type\":\"string\"}").getSchema())
                .build())
            .build();

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchPublishersPort.fetchPublisher(room, "EE/PUB/123")).thenReturn(Optional.of(publisher));
        when(fetchEventTypesPort.fetchEventType(publisher, eventTypeIdentifier)).thenReturn(Optional.of(eventType));

        // When
        var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
        assertThatThrownBy(() -> publishEventsService.execute(request))
            .isInstanceOf(ApiException.class)
            .hasMessage("The event payload is not compatible with the event type");
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsNotFound() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        var publisherId = UUIDGenerator.randomUUID();
        var publisher = Publisher.builder().id(publisherId).build();

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchPublishersPort.fetchPublisher(room, "EE/PUB/123")).thenReturn(Optional.of(publisher));
        when(fetchEventTypesPort.fetchEventType(publisher, eventTypeIdentifier)).thenReturn(Optional.empty());

        // When
        var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
        assertThatThrownBy(() -> publishEventsService.execute(request))
            .isInstanceOf(EventTypeNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenPublisherIsNotFound() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        var roomId = UUIDGenerator.randomUUID();
        var room = Room.builder().id(roomId).build();

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchPublishersPort.fetchPublisher(room, "EE/PUB/123")).thenReturn(Optional.empty());

        // When
        var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
        assertThatThrownBy(() -> publishEventsService.execute(request))
            .isInstanceOf(PublisherNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var roomIdentifier = new RoomIdentifier("PatientPortal");
        var publisherIdentifier = new PublisherIdentifier("EE/PUB/123");
        var eventTypeIdentifier = new EventTypeIdentifier("newPatient");
        var payload = jsonService.readTree("{\"hello\":\"world\"}");

        when(fetchRoomsPort.fetchRoomByIdentifier("PatientPortal")).thenReturn(Optional.empty());

        // When
        var request = new PublishEventsUseCase.Request(roomIdentifier, publisherIdentifier, eventTypeIdentifier, payload);
        assertThatThrownBy(() -> publishEventsService.execute(request))
            .isInstanceOf(RoomNotFoundException.class);
    }
}
