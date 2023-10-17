package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.EventTypeParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.EventTypeVersionParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.EventTypeIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.CreateEventTypeVersionUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.DeleteEventTypeVersionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.GetEventTypeVersionsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.event.UpdateEventTypeVersionUseCase;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.CreateEventTypeVersionsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.DeleteEventTypeVersionOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetEventTypeVersionsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.UpdateEventTypeVersionsOperation;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Event Type Versions", description = "API for event types versions")
public class EventTypeVersionsController {

    private final UserContext userContext;
    private final GetEventTypeVersionsUseCase getEventTypeVersionsUseCase;
    private final CreateEventTypeVersionUseCase createEventTypeVersionUseCase;
    private final UpdateEventTypeVersionUseCase updateEventTypeVersionUseCase;
    private final DeleteEventTypeVersionsUseCase deleteEventTypeVersionsUseCase;

    @GetEventTypeVersionsOperation
    @Operation(summary = "Lists all versions for an event type for PubSub managers accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<List<EventTypeVersionDTO>> getEventTypeVersionsViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        return ResponseEntity.ok(getEventTypeVersions(memberId, roomId, eventType));
    }

    @GetEventTypeVersionsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Lists all versions for an event type for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<List<EventTypeVersionDTO>> getEventTypeVersionsAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        return ResponseEntity.ok(getEventTypeVersions(memberId, roomId, eventType));
    }

    @GetEventTypeVersionsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Lists all versions for an event type for PubSub managers")
    @GetMapping("/api/rooms/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<List<EventTypeVersionDTO>> getEventTypeVersionsAsManager(
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(getEventTypeVersions(manager, roomId, eventType));
    }

    private List<EventTypeVersionDTO> getEventTypeVersions(String manager, String room, String eventType) {
        var request = GetEventTypeVersionsUseCase.Request.builder()
            .managerIdentifier(new ManagerIdentifier(manager))
            .roomIdentifier(new RoomIdentifier(room))
            .eventTypeIdentifier(new EventTypeIdentifier(eventType))
            .build();
        return getEventTypeVersionsUseCase.execute(request)
            .eventTypeVersions()
            .stream()
            .map(EventTypeVersionDTO::fromDomain)
            .toList();
    }

    @CreateEventTypeVersionsOperation
    @Operation(summary = "Creates versions for an event type for PubSub managers accessing via XRoad network")
    @PostMapping("/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> createEventTypeVersionViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody CreateEventTypeVersionDTO createEventTypeVersionDTO) {
        createEventTypeVersionUseCase.execute(createEventTypeVersionDTO.toRequest(memberId, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CreateEventTypeVersionsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Creates versions for an event type for PubSub administrators")
    @PostMapping("/api/admin/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> createEventTypeVersionAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody CreateEventTypeVersionDTO createEventTypeVersionDTO) {
        createEventTypeVersionUseCase.execute(createEventTypeVersionDTO.toRequest(memberId, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CreateEventTypeVersionsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Creates versions for an event type for PubSub managers")
    @PostMapping("/api/rooms/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> createEventTypeVersionAsManager(
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody CreateEventTypeVersionDTO createEventTypeVersionDTO) {
        var manager = userContext.getUsername();
        createEventTypeVersionUseCase.execute(createEventTypeVersionDTO.toRequest(manager, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @UpdateEventTypeVersionsOperation
    @Operation(summary = "Updates versions for an event type for PubSub managers accessing via XRoad network")
    @PutMapping("/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> updateEventTypeVersionViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody UpdateEventTypeVersionDTO updateEventTypeVersionDTO) {
        updateEventTypeVersionUseCase.execute(updateEventTypeVersionDTO.toRequest(memberId, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @UpdateEventTypeVersionsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Updates versions for an event type for PubSub administrators")
    @PutMapping("/api/admin/rooms/{memberId}/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> updateEventTypeVersionAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody UpdateEventTypeVersionDTO updateEventTypeVersionDTO) {
        updateEventTypeVersionUseCase.execute(updateEventTypeVersionDTO.toRequest(memberId, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @UpdateEventTypeVersionsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Updates versions for an event type for PubSub managers")
    @PutMapping("/api/rooms/{roomId}/event-types/{eventType}/versions")
    public ResponseEntity<Void> updateEventTypeVersionAsManager(
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @Valid @RequestBody UpdateEventTypeVersionDTO updateEventTypeVersionDTO) {
        var manager = userContext.getUsername();
        updateEventTypeVersionUseCase.execute(updateEventTypeVersionDTO.toRequest(manager, roomId, eventType));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeVersionOperation
    @Operation(summary = "Deletes an event type version for PubSub managers accessing via XRoad network")
    @DeleteMapping("/rooms/{memberId}/{roomId}/event-types/{eventType}/versions/{version}")
    public ResponseEntity<Void> deleteEventTypeVersionViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @EventTypeVersionParam @PathVariable Integer version) {
        deleteVersion(memberId, roomId, eventType, version);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeVersionOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Deletes an event type version for PubSub administrators")
    @DeleteMapping("/api/admin/rooms/{memberId}/{roomId}/event-types/{eventType}/versions/{version}")
    public ResponseEntity<Void> deleteEventTypeVersionAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @EventTypeVersionParam @PathVariable Integer version) {
        deleteVersion(memberId, roomId, eventType, version);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteEventTypeVersionOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Deletes an event type version for PubSub managers")
    @DeleteMapping("/api/rooms/{roomId}/event-types/{eventType}/versions/{version}")
    public ResponseEntity<Void> deleteEventTypeVersionAsManager(
        @RoomIdParam @PathVariable String roomId,
        @EventTypeParam @PathVariable String eventType,
        @EventTypeVersionParam @PathVariable Integer version) {
        var manager = userContext.getUsername();
        deleteVersion(manager, roomId, eventType, version);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void deleteVersion(String manager, String room, String eventType, Integer version) {
        var request = DeleteEventTypeVersionsUseCase.Request.builder()
            .managerIdentifier(new ManagerIdentifier(manager))
            .roomIdentifier(new RoomIdentifier(room))
            .eventTypeIdentifier(new EventTypeIdentifier(eventType))
            .versionNo(version)
            .build();
        deleteEventTypeVersionsUseCase.execute(request);
    }
}
