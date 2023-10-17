package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.EventTypeNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventTypeID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.CreateSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventTypesPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request.SubscriptionData;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase.Request.SubscriptionData.Parameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PULL;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Method.PUSH;
import static com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status.PENDING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateXRoadSubscriptionServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchEventTypesPort fetchEventTypesPort;

    @Mock
    private CreateSubscriptionsPort createSubscriptionsPort;

    @InjectMocks
    private CreateXRoadSubscriptionService createXRoadSubscriptionService;

    @ParameterizedTest
    @EnumSource(value = IdentifierType.class, names = {"XROAD", "INTERNAL"})
    void shouldCreatePushSubscription(IdentifierType identifierType) {
        // Given
        var request = defaultRequest(identifierType, "PUSH");
        var roomID = new RoomID(UUIDGenerator.randomUUID());

        when(fetchRoomsPort.fetchRoomID(request.getRoom())).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUID.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventType(), roomID)).thenReturn(Optional.of(eventTypeID));

        var subscriptionId = UUIDGenerator.randomUUID();
        when(createSubscriptionsPort.createSubscription(any(Subscription.class))).thenReturn(subscriptionId);

        // Then
        Assertions.assertAll(
            () -> {
                var response = createXRoadSubscriptionService.execute(request);
                assertThat(response.getSubscriptionId(), equalTo(subscriptionId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(Subscription.class);
                verify(createSubscriptionsPort).createSubscription(captor.capture());

                var subscription = captor.getValue();
                assertThat(subscription.getRoomId(), equalTo(roomID.getId()));
                assertThat(subscription.getEventTypeId(), equalTo(eventTypeID.getId()));
                assertThat(subscription.getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(subscription.getIdentifierType(), equalTo(identifierType));
                assertThat(subscription.getStatus(), equalTo(PENDING));
                assertThat(subscription.getParameters().getMethod(), equalTo(PUSH));
                assertThat(subscription.getParameters().getPushUrl(), equalTo("/app/callback"));
                assertThat(subscription.getParameters().getDeliveryDelay(), equalTo(5000));
                assertThat(subscription.getParameters().getDeliveryDelayMultiplier(), equalTo(1.5));
                assertThat(subscription.getParameters().getDeliveryAttempts(), equalTo(10));
            }
        );
    }

    @ParameterizedTest
    @EnumSource(value = IdentifierType.class, names = {"XROAD", "INTERNAL"})
    void shouldCreatePullSubscription(IdentifierType identifierType) {
        // Given
        var request = defaultRequest(identifierType, "PULL");
        var roomID = new RoomID(UUIDGenerator.randomUUID());

        when(fetchRoomsPort.fetchRoomID(request.getRoom())).thenReturn(Optional.of(roomID));

        var eventTypeID = new EventTypeID(UUID.randomUUID());
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventType(), roomID)).thenReturn(Optional.of(eventTypeID));

        var subscriptionId = UUIDGenerator.randomUUID();
        when(createSubscriptionsPort.createSubscription(any(Subscription.class))).thenReturn(subscriptionId);

        // Then
        Assertions.assertAll(
            () -> {
                var response = createXRoadSubscriptionService.execute(request);
                assertThat(response.getSubscriptionId(), equalTo(subscriptionId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(Subscription.class);
                verify(createSubscriptionsPort).createSubscription(captor.capture());

                var subscription = captor.getValue();
                assertThat(subscription.getRoomId(), equalTo(roomID.getId()));
                assertThat(subscription.getEventTypeId(), equalTo(eventTypeID.getId()));
                assertThat(subscription.getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(subscription.getIdentifierType(), equalTo(identifierType));
                assertThat(subscription.getStatus(), equalTo(PENDING));
                assertThat(subscription.getParameters().getMethod(), equalTo(PULL));
                assertNull(subscription.getParameters().getPushUrl());
                assertNull(subscription.getParameters().getDeliveryDelay());
                assertNull(subscription.getParameters().getDeliveryDelayMultiplier());
                assertNull(subscription.getParameters().getDeliveryAttempts());
            }
        );
    }

    @Test
    void shouldThrowExceptionWhenEventTypeIsNotFound() {
        // Given
        var request = defaultRequest(XROAD, "PUSH");
        var roomID = new RoomID(UUIDGenerator.randomUUID());

        when(fetchRoomsPort.fetchRoomID(request.getRoom())).thenReturn(Optional.of(roomID));
        when(fetchEventTypesPort.fetchEventTypeID(request.getEventType(), roomID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> createXRoadSubscriptionService.execute(request)).isInstanceOf(EventTypeNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var request = defaultRequest(XROAD, "PUSH");

        when(fetchRoomsPort.fetchRoomID(request.getRoom())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> createXRoadSubscriptionService.execute(request)).isInstanceOf(RoomNotFoundException.class);
    }

    private static Request defaultRequest(IdentifierType identifierType, String method) {
        return Request.builder()
            .room(new RoomIdentifier("PatientPortal"))
            .eventType(new EventTypeIdentifier("newPatient"))
            .subscription(SubscriptionData.builder()
                .identifier("EE/BUSINESS/123456789")
                .identifierType(identifierType)
                .parameters(Parameters.builder()
                    .method(method)
                    .pushUrl("/app/callback")
                    .deliveryDelay(5000)
                    .deliveryDelayMultiplier(1.5)
                    .deliveryAttempts(10)
                    .build())
                .status("PENDING")
                .build())
            .build();
    }
}
