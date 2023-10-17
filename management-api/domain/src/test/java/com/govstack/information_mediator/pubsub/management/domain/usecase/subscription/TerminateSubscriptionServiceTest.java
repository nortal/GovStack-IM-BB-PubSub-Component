package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.RoomNotFoundException;
import com.govstack.information_mediator.pubsub.commons.exception.SubscriptionNotFoundException;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription.Status;
import com.govstack.information_mediator.pubsub.domain.entity.id.ManagerID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.DeleteSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.TerminateSubscriptionUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TerminateSubscriptionServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;

    @Mock
    private DeleteSubscriptionsPort deleteSubscriptionsPort;

    @InjectMocks
    private TerminateSubscriptionService terminateSubscriptionService;


    @ParameterizedTest
    @ValueSource(strings = {"ACTIVE", "PENDING"})
    void shouldTerminateSubscriptionAsMember(String status) {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();

        var subscription = Subscription.builder().id(subscriptionId).status(Status.valueOf(status)).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(UUIDGenerator.randomUUID())));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId, "EE/BUSINESS/123456789", room))
            .thenReturn(Optional.of(subscription));

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> terminateSubscriptionService.execute(request)),
            () -> verify(deleteSubscriptionsPort).terminateSubscription(subscriptionId)
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"REJECTED", "TERMINATED"})
    void shouldThrowExceptionWhenStatusIsNotValidAsMember(String status) {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();
        var message = "Subscription with status [" + status + "] can not be terminated";

        var subscription = Subscription.builder().id(subscriptionId).status(Status.valueOf(status)).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(UUIDGenerator.randomUUID())));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId, "EE/BUSINESS/123456789", room))
            .thenReturn(Optional.of(subscription));

        // Then
        assertThatThrownBy(() -> terminateSubscriptionService.execute(request))
            .isInstanceOf(ApiException.class)
            .hasMessage(message);
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsNotFoundAsMember() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(UUIDGenerator.randomUUID())));
        when(fetchSubscriptionsPort.fetchSubscription(subscriptionId, "EE/BUSINESS/123456789", room))
            .thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> terminateSubscriptionService.execute(request))
            .isInstanceOf(SubscriptionNotFoundException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"ACTIVE", "PENDING"})
    void shouldTerminateSubscriptionAsManager(String status) {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();

        var subscription = Subscription.builder().id(subscriptionId).status(Status.valueOf(status)).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(managerId)));
        when(fetchSubscriptionsPort.fetchSubscription(any(SubscriptionID.class), any(RoomID.class))).thenReturn(Optional.of(subscription));

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> terminateSubscriptionService.execute(request)),
            () -> verify(deleteSubscriptionsPort).terminateSubscription(subscriptionId),
            () -> verify(fetchSubscriptionsPort, times(0)).fetchSubscription(any(UUID.class), anyString(), any(Room.class))
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {"REJECTED", "TERMINATED"})
    void shouldThrowExceptionWhenStatusIsNotValidAsManager(String status) {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();
        var message = "Subscription with status [" + status + "] can not be terminated";

        var subscription = Subscription.builder().id(subscriptionId).status(Status.valueOf(status)).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(managerId)));
        when(fetchSubscriptionsPort.fetchSubscription(any(SubscriptionID.class), any(RoomID.class))).thenReturn(Optional.of(subscription));

        Assertions.assertAll(
            () ->         assertThatThrownBy(() -> terminateSubscriptionService.execute(request))
                .isInstanceOf(ApiException.class)
                .hasMessage(message),
            () -> verify(fetchSubscriptionsPort, times(0)).fetchSubscription(any(UUID.class), anyString(), any(Room.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsNotFoundAsManager() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerId = UUIDGenerator.randomUUID();
        var room = Room.builder().managerId(managerId).build();

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.of(room));
        when(fetchManagersPort.fetchManagerID(any(ManagerIdentifier.class))).thenReturn(Optional.of(new ManagerID(managerId)));
        when(fetchSubscriptionsPort.fetchSubscription(any(SubscriptionID.class), any(RoomID.class))).thenReturn(Optional.empty());

        // Then
        Assertions.assertAll(
            () -> assertThatThrownBy(() -> terminateSubscriptionService.execute(request)).isInstanceOf(SubscriptionNotFoundException.class),
            () -> verify(fetchSubscriptionsPort, times(0)).fetchSubscription(any(UUID.class), anyString(), any(Room.class))
        );
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);

        when(fetchRoomsPort.fetchRoom("PatientPortal")).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> terminateSubscriptionService.execute(request))
            .isInstanceOf(RoomNotFoundException.class);
    }

    private static Request defaultRequest(UUID subscriptionId) {
        return new Request(
            new RoomIdentifier("PatientPortal"),
            new MemberIdentifier("EE/BUSINESS/123456789"),
            new SubscriptionID(subscriptionId)
        );
    }
}
