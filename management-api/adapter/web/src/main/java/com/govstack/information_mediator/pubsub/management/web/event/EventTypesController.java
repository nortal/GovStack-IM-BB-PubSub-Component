package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.EventTypeParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.ExportEventTypeIdentifiersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypesUseCase;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.CreateEventTypeOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.DeleteEventTypeOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetEventTypeIdentifiersOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetEventTypesOperation;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Event Types", description = "API for event types")
class EventTypesController {

    private final UserContext userContext;
    private final GetEventTypesUseCase getEventTypesUseCase;
    private final CreateEventTypesUseCase createEventTypesUseCase;
    private final DeleteEventTypesUseCase deleteEventTypesUseCase;
    private final ExportEventTypeIdentifiersUseCase exportEventTypeIdentifiersUseCase;

    @GetEventTypesOperation
    @Operation(summary = "Get event types available in a message room for PubSub managers accessing via XRoad network")
    @GetMapping(value = "/rooms/{memberId}/{roomId}/event-types")
    public ResponseEntity<PagedEventTypeViewsDTO> getEventTypesViaXRoadNetwork(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(
            getEventTypes(
                new ManagerIdentifier(memberId),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    @GetEventTypesOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Get event types available in a message room for PubSub administrators")
    @GetMapping(value = "/api/admin/rooms/{memberId}/{roomId}/event-types")
    public ResponseEntity<PagedEventTypeViewsDTO> getEventTypesAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(
            getEventTypes(
                new ManagerIdentifier(memberId),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    @GetEventTypesOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Get event types available in a message room for PubSub managers")
    @GetMapping(value = "/api/rooms/{roomId}/event-types")
    public ResponseEntity<PagedEventTypeViewsDTO> getEventTypesAsManager(
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(
            getEventTypes(
                new ManagerIdentifier(manager),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    private PagedEventTypeViewsDTO getEventTypes(
        ManagerIdentifier manager,
        RoomIdentifier room,
        PagingSortingParametersDTO params) {

        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(params.getDesc()))
            .maxItemsPerPage(params.getLimit())
            .pageOffset(params.getOffset())
            .sortBy(params.getSortBy())
            .filterQuery(params.getFilterBy())
            .build();

        return PagedEventTypeViewsDTO.from(getEventTypesUseCase.execute(new GetEventTypesUseCase.Request(manager, room, pageRequest)).eventTypes());
    }

    @CreateEventTypeOperation
    @Operation(summary = "Creates an event type in a message room for PubSub managers accessing via XRoad network")
    @PostMapping("/rooms/{memberId}/{roomId}/event-types")
    public ResponseEntity<Void> createEventTypeViaXRoadNetwork(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody CreateEventTypeDTO createEventTypeDTO) {
        createEventTypesUseCase.execute(createEventTypeDTO.toRequest(memberId, roomId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CreateEventTypeOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Creates an event type in a message room for PubSub administrators")
    @PostMapping("/api/admin/rooms/{memberId}/{roomId}/event-types")
    public ResponseEntity<Void> createEventTypeAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody CreateEventTypeDTO createEventTypeDTO) {
        createEventTypesUseCase.execute(createEventTypeDTO.toRequest(memberId, roomId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CreateEventTypeOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Creates an event type in a message room for PubSub managers")
    @PostMapping("/api/rooms/{roomId}/event-types")
    public ResponseEntity<Void> createEventTypeAsManager(
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody CreateEventTypeDTO createEventTypeDTO) {
        var manager = userContext.getUsername();
        createEventTypesUseCase.execute(createEventTypeDTO.toRequest(manager, roomId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeOperation
    @Operation(summary = "Deletes an event type in a message room for PubSub manages accessing via XRoad")
    @DeleteMapping("/rooms/{memberId}/{roomId}/event-types/{eventType}")
    public ResponseEntity<Void> deleteEventTypeViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        deleteEventType(memberId, roomId, eventType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Deletes an event type in a message room for PubSub administrators")
    @DeleteMapping("/api/admin/rooms/{memberId}/{roomId}/event-types/{eventType}")
    public ResponseEntity<Void> deleteEventTypeAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        deleteEventType(memberId, roomId, eventType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Deletes an event type in a message room for PubSub managers")
    @DeleteMapping("/api/rooms/{roomId}/event-types/{eventType}")
    public ResponseEntity<Void> deleteEventTypeAsManager(
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        var manager = userContext.getUsername();
        deleteEventType(manager, roomId, eventType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteEventType(String manager, String room, String eventType) {
        var request = DeleteEventTypesUseCase.Request.builder()
            .manager(DeleteEventTypesUseCase.Request.ManagerData.of(manager))
            .room(DeleteEventTypesUseCase.Request.RoomData.of(room))
            .eventType(DeleteEventTypesUseCase.Request.EventTypeData.of(eventType))
            .build();
        deleteEventTypesUseCase.execute(request);
    }

    @GetEventTypeIdentifiersOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Export all event type identifiers in a message room for PubSub administrators")
    @GetMapping("/api/admin/export/rooms/{memberId}/{roomId}/event-types")
    public ResponseEntity<List<String>> exportEventTypeIdentifiersAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId) {
        return ResponseEntity.ok(exportEventTypeIdentifiers(
            new ManagerIdentifier(memberId), new RoomIdentifier(roomId)));
    }

    @GetEventTypeIdentifiersOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Export all event type identifiers in a message room for PubSub managers")
    @GetMapping("/api/export/rooms/{roomId}/event-types")
    public ResponseEntity<List<String>> exportEventTypeIdentifiersAsManager(
        @RoomIdParam @PathVariable String roomId) {
        return ResponseEntity.ok(exportEventTypeIdentifiers(
            new ManagerIdentifier(userContext.getUsername()), new RoomIdentifier(roomId)));
    }

    public List<String> exportEventTypeIdentifiers(ManagerIdentifier manager, RoomIdentifier room) {
        var request = new ExportEventTypeIdentifiersUseCase.Request(manager, room);
        return exportEventTypeIdentifiersUseCase.execute(request).eventTypes();
    }
}
