package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator;
import com.govstack.information_mediator.pubsub.domain.entity.EventType;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ApproveSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.RejectSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.TerminateSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.web.configuration.XRoadPushUriBuilder;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SubscriptionsControllerTest {

    @Mock
    private ListSubscriptionsUseCase listSubscriptionsUseCase;

    @Mock
    private CreateXRoadSubscriptionUseCase createXRoadSubscriptionUseCase;

    @Mock
    private TerminateSubscriptionUseCase terminateSubscriptionUseCase;

    @Mock
    private ApproveSubscriptionUseCase approveSubscriptionUseCase;

    @Mock
    private RejectSubscriptionUseCase rejectSubscriptionUseCase;

    @Mock
    private XRoadPushUriBuilder xRoadPushUriBuilder;

    @InjectMocks
    private SubscriptionsController subscriptionsController;

    @Test
    void testListSubscriptionsUseCaseIsCalledCorrectly() {
        // Given
        var roomId = "PatientPortal";
        var subscriptionView = SubscriptionView.builder()
            .id(UUID.randomUUID())
            .room(Room.builder().build())
            .eventType(EventType.builder().identifier("eventType-1").build())
            .identifier("EE/BUSINESS/123")
            .parameters(Subscription.Parameters.builder()
                .method(Subscription.Method.PULL)
                .build())
            .status(Subscription.Status.ACTIVE)
            .build();

        var subscriptionResponse = SubscriptionResponseDTO.fromView(subscriptionView);
        var subscriptions = List.of(subscriptionView);
        var params = new PagingSortingParametersDTO();
        var page = Page.<SubscriptionView>builder().content(subscriptions).build();
        var pageRequest = PageRequest.builder().build();

        when(listSubscriptionsUseCase.execute(ListSubscriptionsUseCase.Request.of(
            ListSubscriptionsUseCase.Request.RoomData.of(roomId),
            ListSubscriptionsUseCase.Request.MemberData.of("EE/BUSINESS/123456789"),
            Subscription.Status.ACTIVE,
            pageRequest
        )))
            .thenReturn(new ListSubscriptionsUseCase.Response(page));

        // When
        var response = subscriptionsController.listSubscriptionsViaXRoad("EE/BUSINESS/123456789", roomId, Subscription.Status.ACTIVE, params);

        // Then
        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
        assertThat(Objects.requireNonNull(response.getBody()).getSubscriptions(), equalTo(List.of(subscriptionResponse)));

        var captor = ArgumentCaptor.forClass(ListSubscriptionsUseCase.Request.class);
        verify(listSubscriptionsUseCase).execute(captor.capture());

        var request = captor.getValue();
        assertThat(request.getRoom().getIdentifier(), equalTo(roomId));
    }

    @Test
    void testCreateMemberSubscriptionUseCaseIsCalledCorrectly() {
        // Given
        var createSubscriptionDTO = CreateXRoadSubscriptionDTO.builder()
            .eventType("newPatient")
            .method("PUSH")
            .pushUrl("/app/callback")
            .eventType("newPatient")
            .deliveryDelay(5000)
            .deliveryDelayMultiplier(1.5)
            .deliveryAttempts(10)
            .build();

        // When
        var subscriptionId = UUID.randomUUID();
        var createSubscriptionResponse = CreateXRoadSubscriptionUseCase.Response.of(subscriptionId);
        when(createXRoadSubscriptionUseCase.execute(any(CreateXRoadSubscriptionUseCase.Request.class))).thenReturn(createSubscriptionResponse);
        when(xRoadPushUriBuilder.getFullPathForServiceEndpoint(any(), any())).thenReturn("/app/callback");

        // Then
        Assertions.assertAll(
            () -> {
                var response = subscriptionsController.createXRoadSubscriptionViaXRoad("EE/BUSINESS/123456789",
                    "PatientPortal", createSubscriptionDTO, "EE/BUSINESS/123456789");
                assertThat(response.getBody().getSubscriptionId(), equalTo(subscriptionId));
            },
            () -> {
                var captor = ArgumentCaptor.forClass(CreateXRoadSubscriptionUseCase.Request.class);
                verify(createXRoadSubscriptionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.getRoom().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.getEventType().getIdentifier(), equalTo("newPatient"));
                assertThat(request.getEventType().getIdentifier(), equalTo("newPatient"));
                assertThat(request.getSubscription().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.getSubscription().getIdentifierType(), equalTo(XROAD));
                assertThat(request.getSubscription().getParameters().getMethod(), equalTo("PUSH"));
                assertThat(request.getSubscription().getParameters().getPushUrl(), equalTo("/app/callback"));
                assertThat(request.getSubscription().getParameters().getDeliveryDelay(), equalTo(5000));
                assertThat(request.getSubscription().getParameters().getDeliveryDelayMultiplier(), equalTo(1.5));
                assertThat(request.getSubscription().getParameters().getDeliveryAttempts(), equalTo(10));
            }
        );
    }

    @Test
    void testTerminateSubscriptionUseCaseIsCalledCorrectly() {
        // Given
        var subscriptionId = UUIDGenerator.randomUUID();

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> subscriptionsController.terminateSubscriptionViaXRoad("EE/BUSINESS/123456789", "PatientPortal", subscriptionId)),
            () -> {
                var captor = ArgumentCaptor.forClass(TerminateSubscriptionUseCase.Request.class);
                verify(terminateSubscriptionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.room().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.member().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.subscriptionID().getId(), equalTo(subscriptionId));
            }
        );
    }

    @Test
    void testApproveSubscriptionUseCaseIsCalledCorrectly() {
        // Given
        var subscriptionId = UUID.randomUUID();
        var status = SubscriptionStatusDTO.builder().status(Subscription.Status.ACTIVE).build();

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> subscriptionsController.updateSubscriptionStatusViaXRoad("EE/BUSINESS/123456789", "PatientPortal", subscriptionId, status)),
            () -> {
                var captor = ArgumentCaptor.forClass(ApproveSubscriptionUseCase.Request.class);
                verify(approveSubscriptionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.room().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.manager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.subscriptionID().getId(), equalTo(subscriptionId));
            }
        );
    }

    @Test
    void testRejectSubscriptionUseCaseIsCalledCorrectly() {
        // Given
        var subscriptionId = UUID.randomUUID();
        var status = SubscriptionStatusDTO.builder().status(Subscription.Status.REJECTED).build();

        // Then
        Assertions.assertAll(
            () -> assertThatNoException().isThrownBy(() -> subscriptionsController.updateSubscriptionStatusViaXRoad("EE/BUSINESS/123456789", "PatientPortal", subscriptionId, status)),
            () -> {
                var captor = ArgumentCaptor.forClass(RejectSubscriptionUseCase.Request.class);
                verify(rejectSubscriptionUseCase).execute(captor.capture());

                var request = captor.getValue();
                assertThat(request.room().getIdentifier(), equalTo("PatientPortal"));
                assertThat(request.manager().getIdentifier(), equalTo("EE/BUSINESS/123456789"));
                assertThat(request.subscriptionID().getId(), equalTo(subscriptionId));
            }
        );
    }

}
