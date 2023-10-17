package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.EventIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.EventID;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventDetailsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventsViewUseCase;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetEventDetailsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetEventsViewOperation;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Event", description = "API for eventsPage")
class EventsController {

    private final UserContext userContext;
    private final GetEventDetailsUseCase getEventDetailsUseCase;
    private final GetEventsViewUseCase getEventsViewUseCase;

    @GetEventDetailsOperation
    @Operation(summary = "Get event details for PubSub managers accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/events/{eventId}")
    ResponseEntity<EventDetailsDTO> getEventDetailsViaXRoad(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @EventIdParam @PathVariable UUID eventId) {
        return ResponseEntity.ok(getEventDetails(memberId, roomId, eventId));
    }

    @GetEventDetailsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Get event details for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/events/{eventId}")
    ResponseEntity<EventDetailsDTO> getEventDetailsAsAdministrator(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @EventIdParam @PathVariable UUID eventId) {
        return ResponseEntity.ok(getEventDetails(memberId, roomId, eventId));
    }

    @GetEventDetailsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Get event details for PubSub managers")
    @GetMapping("/api/rooms/{roomId}/events/{eventId}")
    ResponseEntity<EventDetailsDTO> getEventDetailsAsManager(
            @RoomIdParam @PathVariable String roomId,
            @EventIdParam @PathVariable UUID eventId) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(getEventDetails(manager, roomId, eventId));
    }

    private EventDetailsDTO getEventDetails(String managerIdentifier, String roomIdentifier, UUID eventId) {
        var manager = new ManagerIdentifier(managerIdentifier);
        var room = new RoomIdentifier(roomIdentifier);
        var event = new EventID(eventId);
        var response = getEventDetailsUseCase.execute(new GetEventDetailsUseCase.Request(manager, room, event));
        return EventDetailsDTO.fromResponse(response);
    }

    @GetEventsViewOperation
    @Operation(summary = "Get eventsPage view for PubSub managers accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/events")
    ResponseEntity<PagedEventsDTO> getEventsViewViaXRoad(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(required = false) UUID publisherId,
            @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
            @RequestParam(required = false) String publisherIdentifier,
            @RequestParam(required = false) String eventTypeIdentifier,
            @RequestParam(required = false) Integer eventTypeVersion) {
        return ResponseEntity.ok(getEventsView(memberId, roomId, from, to, publisherId, pagingSortingParameters,
                publisherIdentifier, eventTypeIdentifier, eventTypeVersion));
    }

    @GetEventsViewOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Get eventsPage view for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/events")
    ResponseEntity<PagedEventsDTO> getEventsViewAsAdministrator(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(required = false) UUID publisherId,
            @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
            @RequestParam(required = false) String publisherIdentifier,
            @RequestParam(required = false) String eventTypeIdentifier,
            @RequestParam(required = false) Integer eventTypeVersion) {
        return ResponseEntity.ok(getEventsView(memberId, roomId, from, to, publisherId, pagingSortingParameters,
                publisherIdentifier, eventTypeIdentifier, eventTypeVersion));
    }

    @GetEventsViewOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Get eventsPage view  for PubSub managers")
    @GetMapping("/api/rooms/{roomId}/events")
    ResponseEntity<PagedEventsDTO> getEventsViewAsManager(
            @RoomIdParam @PathVariable String roomId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to,
            @RequestParam(required = false) UUID publisherId,
            @ModelAttribute PagingSortingParametersDTO pagingSortingParameters,
            @RequestParam(required = false) String publisherIdentifier,
            @RequestParam(required = false) String eventTypeIdentifier,
            @RequestParam(required = false) Integer eventTypeVersion) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(getEventsView(manager, roomId, from, to, publisherId, pagingSortingParameters,
                publisherIdentifier, eventTypeIdentifier, eventTypeVersion));
    }

    private PagedEventsDTO getEventsView(String memberId, String roomId, Date from, Date to, UUID publisherId,
                                         PagingSortingParametersDTO pagingSortingParameters,
                                         String publisherIdentifier, String eventTypeIdentifier, Integer eventTypeVersion) {
        var publisher = publisherId != null ? new PublisherID(publisherId) : null;

        var pageRequest = PageRequest.builder()
                .descendingOrder(Boolean.TRUE.equals(pagingSortingParameters.getDesc()))
                .maxItemsPerPage(pagingSortingParameters.getLimit())
                .pageOffset(pagingSortingParameters.getOffset())
                .sortBy(pagingSortingParameters.getSortBy())
                .build();

        var request = GetEventsViewUseCase.Request.builder()
                .manager(new ManagerIdentifier(memberId))
                .room(new RoomIdentifier(roomId))
                .publisher(publisher)
                .pageRequest(pageRequest)
                .fromDate(from.toInstant())
                .toDate(to.toInstant())
                .eventTypeVersion(eventTypeVersion)
                .eventTypeIdentifier(eventTypeIdentifier)
                .publisherIdentifier(publisherIdentifier)
                .build();

        return PagedEventsDTO.from(getEventsViewUseCase.execute(request).eventsPage());
    }
}
