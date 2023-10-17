package com.govstack.information_mediator.pubsub.management.domain.usecase.subscription;

import com.govstack.information_mediator.pubsub.commons.exception.ApiException;
import com.govstack.information_mediator.pubsub.commons.exception.ManagerNotFoundException;
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
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchManagersPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchRoomsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchSubscriptionsPort;
import com.govstack.information_mediator.pubsub.management.domain.port.SubscriptionStatusPort;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ApproveSubscriptionUseCase.Request;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApproveSubscriptionServiceTest {

    @Mock
    private FetchRoomsPort fetchRoomsPort;

    @Mock
    private FetchManagersPort fetchManagersPort;

    @Mock
    private FetchSubscriptionsPort fetchSubscriptionsPort;

    @Mock
    private SubscriptionStatusPort subscriptionStatusPort;

    @InjectMocks
    private ApproveSubscriptionService approveSubscriptionService;

    @Test
    void shouldApproveSubscription() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var managerID = new ManagerID(UUIDGenerator.randomUUID());

        var subscription = Subscription.builder().id(subscriptionId).status(Status.PENDING).build();

        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.of(roomID));
        when(fetchSubscriptionsPort.fetchSubscription(request.subscriptionID(), roomID)).thenReturn(Optional.of(subscription));

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> approveSubscriptionService.execute(request)),
            () -> verify(subscriptionStatusPort).setSubscriptionStatus(subscriptionId, Status.ACTIVE)
        );
    }

    @ParameterizedTest
    @EnumSource(
        value = Subscription.Status.class,
        names = {"PENDING"},
        mode = EnumSource.Mode.EXCLUDE
    )
    void shouldThrowExceptionWhenStatusIsNotPending(Subscription.Status status) {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var managerID = new ManagerID(UUIDGenerator.randomUUID());
        var message = "Subscription with status [" + status + "] can not be approved";

        var subscription = Subscription.builder().id(subscriptionId).status(status).build();

        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.of(roomID));
        when(fetchSubscriptionsPort.fetchSubscription(request.subscriptionID(), roomID)).thenReturn(Optional.of(subscription));

        // Then
        assertThatThrownBy(() -> approveSubscriptionService.execute(request))
            .isInstanceOf(ApiException.class)
            .hasMessage(message);
    }

    @Test
    void shouldThrowExceptionWhenSubscriptionIsNotFound() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var roomID = new RoomID(UUIDGenerator.randomUUID());
        var managerID = new ManagerID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.of(roomID));
        when(fetchSubscriptionsPort.fetchSubscription(request.subscriptionID(), roomID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> approveSubscriptionService.execute(request))
            .isInstanceOf(SubscriptionNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenRoomIsNotFound() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);
        var managerID = new ManagerID(UUIDGenerator.randomUUID());

        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.of(managerID));
        when(fetchRoomsPort.fetchRoomID(request.room(), managerID)).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> approveSubscriptionService.execute(request))
            .isInstanceOf(RoomNotFoundException.class);
    }

    @Test
    void shouldThrowExceptionWhenManagerIsNotFound() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();
        var request = defaultRequest(subscriptionId);

        when(fetchManagersPort.fetchManagerID(request.manager())).thenReturn(Optional.empty());

        // Then
        assertThatThrownBy(() -> approveSubscriptionService.execute(request))
            .isInstanceOf(ManagerNotFoundException.class);
    }

    private static Request defaultRequest(UUID subscriptionId) {
        return new Request(
            new RoomIdentifier("PatientPortal"),
            new ManagerIdentifier("EE/BUSINESS/123456789"),
            new SubscriptionID(subscriptionId)
        );
    }
}
