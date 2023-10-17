package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.Subscription;
import com.govstack.information_mediator.pubsub.domain.entity.id.SubscriptionID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ApproveSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.CreateXRoadSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionEventsDeliveriesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.ListSubscriptionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.RejectSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.subscription.TerminateSubscriptionUseCase;
import com.govstack.information_mediator.pubsub.management.web.configuration.XRoadPushUriBuilder;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.ApproveOrRejectSubscriptionOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.CreateSubscriptionOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetSubscriptionEventsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetSubscriptionsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.TerminateSubscriptionOperation;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Subscriptions", description = "API for managing subscriptions")
class SubscriptionsController {

    public static final String X_ROAD_CLIENT_HEADER_KEY = "X-Road-Client";

    private final UserContext userContext;
    private final CreateXRoadSubscriptionUseCase createXRoadSubscriptionUseCase;
    private final ListSubscriptionsUseCase listSubscriptionsUseCase;
    private final TerminateSubscriptionUseCase terminateSubscriptionUseCase;
    private final ApproveSubscriptionUseCase approveSubscriptionUseCase;
    private final RejectSubscriptionUseCase rejectSubscriptionUseCase;
    private final ListSubscriptionEventsDeliveriesUseCase listSubscriptionEventsDeliveriesUseCase;
    private final XRoadPushUriBuilder xRoadPushUriBuilder;

    @CreateSubscriptionOperation
    @Operation(summary = "A PubSub member can subscribe to existing active message room via XRoad network")
    @PostMapping("/rooms/{memberId}/{roomId}/subscriptions")
    ResponseEntity<CreateSubscriptionResponseDTO> createXRoadSubscriptionViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody CreateXRoadSubscriptionDTO createSubscriptionDTO,
        @RequestHeader(X_ROAD_CLIENT_HEADER_KEY) String xRoadClient) {

        if (Objects.equals(createSubscriptionDTO.getMethod(), "PUSH")) {
            var fullPath = xRoadPushUriBuilder.getFullPathForServiceEndpoint(xRoadClient, createSubscriptionDTO.getPushUrl());
            createSubscriptionDTO.setPushUrl(fullPath);
        }
        var request = createSubscriptionDTO.toRequestWith(memberId, roomId);
        return ResponseEntity.ok(CreateSubscriptionResponseDTO.from(createXRoadSubscriptionUseCase.execute(request)));
    }

    @GetSubscriptionsOperation
    @Operation(summary = "List all subscriptions in a message room for PubSub members accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/subscriptions")
    ResponseEntity<PagedSubscriptionResponseDTO> listSubscriptionsViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @RequestParam(value = "status", required = false) Subscription.Status status,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(listSubscriptions(memberId, roomId, status, pagingSortingParameters));
    }

    @GetSubscriptionsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "List all subscriptions in a message room for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/subscriptions")
    ResponseEntity<PagedSubscriptionResponseDTO> listSubscriptionsAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @RequestParam(value = "status", required = false) Subscription.Status status,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(listSubscriptions(memberId, roomId, status, pagingSortingParameters));
    }

    @GetSubscriptionsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "List all subscriptions in a message room for PubSub manager")
    @GetMapping("/api/rooms/{roomId}/subscriptions")
    ResponseEntity<PagedSubscriptionResponseDTO> listSubscriptionsAsManager(
        @RoomIdParam @PathVariable String roomId,
        @RequestParam(value = "status", required = false) Subscription.Status status,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        String manager = userContext.getUsername();
        return ResponseEntity.ok(listSubscriptions(manager, roomId, status, pagingSortingParameters));
    }

    private PagedSubscriptionResponseDTO listSubscriptions(String member, String roomIdentifier, Subscription.Status status, PagingSortingParametersDTO params) {
        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(params.getDesc()))
            .maxItemsPerPage(params.getLimit())
            .pageOffset(params.getOffset())
            .sortBy(params.getSortBy())
            .filterQuery(params.getFilterBy())
            .build();

        var response = listSubscriptionsUseCase.execute(ListSubscriptionsUseCase.Request.of(
            ListSubscriptionsUseCase.Request.RoomData.of(roomIdentifier),
            ListSubscriptionsUseCase.Request.MemberData.of(member),
            status,
            pageRequest));

        return PagedSubscriptionResponseDTO.from(response.subscriptions());
    }

    @TerminateSubscriptionOperation
    @Operation(summary = "Unsubscribe a PubSub member's subscription from existing active message room via XRoad network")
    @DeleteMapping("/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> terminateSubscriptionViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId) {
        terminateSubscription(memberId, roomId, subscriptionId);
        return ResponseEntity.ok().build();
    }

    @TerminateSubscriptionOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Unsubscribe a PubSub member's subscription from existing active message room as PubSub administrator")
    @DeleteMapping("/api/admin/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> terminateSubscriptionAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId) {
        terminateSubscription(memberId, roomId, subscriptionId);
        return ResponseEntity.ok().build();
    }

    @TerminateSubscriptionOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Unsubscribe a PubSub member's subscription from existing message room as PubSUb manager")
    @DeleteMapping("/api/rooms/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> terminateSubscriptionAsManager(
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId) {
        var manager = userContext.getUsername();
        terminateSubscription(manager, roomId, subscriptionId);
        return ResponseEntity.ok().build();
    }

    private void terminateSubscription(String member, String room, UUID subscriptionId) {
        terminateSubscriptionUseCase.execute(
            new TerminateSubscriptionUseCase.Request(
                new RoomIdentifier(room),
                new MemberIdentifier(member),
                new SubscriptionID(subscriptionId)));
    }

    @ApproveOrRejectSubscriptionOperation
    @Operation(summary = "Approve or reject a PubSub member's subscription via XRoad network")
    @PostMapping("/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> updateSubscriptionStatusViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId,
        @Valid @RequestBody SubscriptionStatusDTO subscriptionStatusDTO) {
        return updateSubscriptionStatus(memberId, roomId, subscriptionId, subscriptionStatusDTO);
    }

    @ApproveOrRejectSubscriptionOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Approve or reject a PubSub member's subscription as PubSub administrator")
    @PostMapping("/api/admin/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> updateSubscriptionStatusAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId,
        @Valid @RequestBody SubscriptionStatusDTO subscriptionStatusDTO) {
        return updateSubscriptionStatus(memberId, roomId, subscriptionId, subscriptionStatusDTO);
    }

    @ApproveOrRejectSubscriptionOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Approve or reject a PubSub member's subscription as PubSub manager")
    @PostMapping("/api/rooms/{roomId}/subscriptions/{subscriptionId}")
    ResponseEntity<Void> updateSubscriptionStatusAsManager(
        @RoomIdParam @PathVariable String roomId,
        @Parameter(description = "The subscription id that belongs to the member") @PathVariable UUID subscriptionId,
        @Valid @RequestBody SubscriptionStatusDTO subscriptionStatusDTO) {
        var manager = userContext.getUsername();
        return updateSubscriptionStatus(manager, roomId, subscriptionId, subscriptionStatusDTO);
    }

    @GetSubscriptionEventsOperation
    @Operation(summary = "List all subscription events delivery info accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}/queue")
    ResponseEntity<PagedSubscriptionEventDeliveriesResponseDTO> listSubscriptionEventDeliveriesViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @PathVariable UUID subscriptionId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
        @RequestParam(required = false) UUID eventId,
        @RequestParam(required = false) String eventTypeIdentifier,
        @RequestParam(required = false) String deliveryStatus) {
        return ResponseEntity.ok(listSubscriptionEventDeliveries(memberId, roomId, subscriptionId,
            pagingSortingParameters, from, to, eventId, eventTypeIdentifier, deliveryStatus));
    }

    @GetSubscriptionEventsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "List all subscription events delivery info for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}/queue")
    ResponseEntity<PagedSubscriptionEventDeliveriesResponseDTO> listSubscriptionEventDeliveriesAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @PathVariable UUID subscriptionId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
        @RequestParam(required = false) UUID eventId,
        @RequestParam(required = false) String eventTypeIdentifier,
        @RequestParam(required = false) String deliveryStatus) {
        return ResponseEntity.ok(listSubscriptionEventDeliveries(memberId, roomId, subscriptionId,
            pagingSortingParameters, from, to, eventId, eventTypeIdentifier, deliveryStatus));
    }

    @GetSubscriptionEventsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "List all subscription events delivery info for PubSub manager")
    @GetMapping("/api/rooms/{roomId}/subscriptions/{subscriptionId}/queue")
    ResponseEntity<PagedSubscriptionEventDeliveriesResponseDTO> listSubscriptionEventDeliveriesAsManager(
        @RoomIdParam @PathVariable String roomId,
        @PathVariable UUID subscriptionId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
        @RequestParam(required = false) UUID eventId,
        @RequestParam(required = false) String eventTypeIdentifier,
        @RequestParam(required = false) String deliveryStatus) {
        String manager = userContext.getUsername();
        return ResponseEntity.ok(listSubscriptionEventDeliveries(manager, roomId, subscriptionId, pagingSortingParameters,
            from, to, eventId, eventTypeIdentifier, deliveryStatus));
    }

    private PagedSubscriptionEventDeliveriesResponseDTO listSubscriptionEventDeliveries(String member,
                                                                                        String roomIdentifier,
                                                                                        UUID subscriptionId,
                                                                                        PagingSortingParametersDTO params,
                                                                                        Date from,
                                                                                        Date to,
                                                                                        UUID eventId,
                                                                                        String eventTypeIdentifier,
                                                                                        String deliveryStatus) {
        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(params.getDesc()))
            .maxItemsPerPage(params.getLimit())
            .pageOffset(params.getOffset())
            .sortBy(params.getSortBy())
            .filterQuery(params.getFilterBy())
            .build();

        var response = listSubscriptionEventsDeliveriesUseCase.execute(ListSubscriptionEventsDeliveriesUseCase.Request.of(
            ListSubscriptionEventsDeliveriesUseCase.Request.RoomData.of(roomIdentifier),
            ListSubscriptionEventsDeliveriesUseCase.Request.MemberData.of(member),
            subscriptionId,
            pageRequest,
            from != null ? from.toInstant() : null,
            to != null ? to.toInstant() : null,
            eventId,
            eventTypeIdentifier,
            deliveryStatus));

        return PagedSubscriptionEventDeliveriesResponseDTO.from(response.subscriptionEventDeliveries());
    }

    private ResponseEntity<Void> updateSubscriptionStatus(
        String manager,
        String room,
        UUID subscriptionId,
        SubscriptionStatusDTO subscriptionStatusDTO) {

        return switch (subscriptionStatusDTO.getStatus()) {
            case ACTIVE -> approveSubscription(manager, room, subscriptionId);
            case REJECTED -> rejectSubscription(manager, room, subscriptionId);
            default -> ResponseEntity.badRequest().build();
        };
    }

    private ResponseEntity<Void> approveSubscription(String manager, String room, UUID subscriptionId) {
        approveSubscriptionUseCase.execute(new ApproveSubscriptionUseCase.Request(
            new RoomIdentifier(room),
            new ManagerIdentifier(manager),
            new SubscriptionID(subscriptionId)
        ));
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<Void> rejectSubscription(String manager, String room, UUID subscriptionId) {
        rejectSubscriptionUseCase.execute(new RejectSubscriptionUseCase.Request(
            new RoomIdentifier(room),
            new ManagerIdentifier(manager),
            new SubscriptionID(subscriptionId)
        ));
        return ResponseEntity.ok().build();
    }

}
