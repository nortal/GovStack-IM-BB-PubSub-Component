package com.govstack.information_mediator.pubsub.management.web.room;

import com.govstack.information_mediator.pubsub.commons.exception.BusinessViolationException;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.CreateRoomUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.GetRoomDetailsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.ListRoomsUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.ModifyRoomUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.room.TerminateRoomsUseCase;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.CreateRoomOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetRoomDetailsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetRoomsOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.ModifyRoomOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.TerminateRoomOperation;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.govstack.information_mediator.pubsub.commons.validation.Violation.ROOM_IDENTIFIER_MODIFICATION_NOT_ALLOWED;
import static com.govstack.information_mediator.pubsub.commons.validation.Violation.ROOM_MANAGER_MODIFICATION_NOT_ALLOWED;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Rooms", description = "API for managing rooms")
class RoomsController {

    private final UserContext userContext;
    private final CreateRoomUseCase createRoomUseCase;
    private final GetRoomDetailsUseCase getRoomDetailsUseCase;
    private final ListRoomsUseCase listRoomsUseCase;
    private final ModifyRoomUseCase modifyRoomUseCase;
    private final TerminateRoomsUseCase terminateRoomsUseCase;

    @CreateRoomOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Create a new room")
    @PostMapping(value = "/api/admin/rooms")
    ResponseEntity<CreateRoomResponseDTO> createRoom(@Valid @RequestBody CreateRoomDTO createRoomDTO) {
        var response = createRoomUseCase.execute(createRoomDTO.toRequest());
        return ResponseEntity.ok(CreateRoomResponseDTO.from(response));
    }

    @GetRoomsOperation
    @Operation(summary = "Retrieves all rooms for PubSub managers accessing via XRoad network")
    @GetMapping(value = "/rooms/{memberId}")
    ResponseEntity<PagedRoomsDTO> listRoomsViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(listRooms(memberId, pagingSortingParameters));
    }

    @GetRoomsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Retrieves all rooms (or rooms of a specific manager) for PubSub administrator")
    @GetMapping(value = "/api/admin/rooms")
    ResponseEntity<PagedRoomsDTO> listRoomsAsAdministrator(
        @RequestParam(value = "managerIdentifier", required = false) String managerIdentifier,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(listRooms(managerIdentifier, pagingSortingParameters));
    }

    @GetRoomsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Retrieves all rooms for PubSub managers")
    @GetMapping(value = "/api/rooms/")
    ResponseEntity<PagedRoomsDTO> listRoomsAsManager(
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(listRooms(manager, pagingSortingParameters));
    }

    private PagedRoomsDTO listRooms(String manager, PagingSortingParametersDTO params) {
        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(params.getDesc()))
            .maxItemsPerPage(params.getLimit())
            .pageOffset(params.getOffset())
            .sortBy(params.getSortBy())
            .filterQuery(params.getFilterBy())
            .build();

        return PagedRoomsDTO.from(listRoomsUseCase.execute(ListRoomsUseCase.Request.of(manager, pageRequest)).rooms());
    }

    @GetRoomDetailsOperation
    @Operation(summary = "Get room full details for PubSub managers accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}")
    ResponseEntity<RoomFullResponseDTO> getRoomDetailsViaXRoad(
        @MemberIdParam@PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId) {
        return ResponseEntity.ok(getRoomDetails(memberId, roomId));
    }

    @GetRoomDetailsOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Get room full details for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}")
    ResponseEntity<RoomFullResponseDTO> getRoomDetailsAsAdministrator(
        @MemberIdParam@PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId) {
        return ResponseEntity.ok(getRoomDetails(memberId, roomId));
    }

    @GetRoomDetailsOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Get room full details for PubSub managers")
    @GetMapping("/api/rooms/{roomId}")
    ResponseEntity<RoomFullResponseDTO> getRoomDetailsAsManager(
        @RoomIdParam @PathVariable String roomId) {
        var manager = userContext.getUsername();
        return ResponseEntity.ok(getRoomDetails(manager, roomId));
    }

    private RoomFullResponseDTO getRoomDetails(String manager, String room) {
        var response = getRoomDetailsUseCase.execute(
            GetRoomDetailsUseCase.Request.builder()
                .manager(GetRoomDetailsUseCase.Request.ManagerData.of(manager))
                .room(GetRoomDetailsUseCase.Request.RoomData.of(room))
                .build());
        return RoomFullResponseDTO.fromDetailedView(response.getRoom(), response.getManager());
    }

    @ModifyRoomOperation
    @Operation(summary = "Modify an existing room for PubSub managers accessing via XRoad network")
    @PutMapping("/rooms/{memberId}/{roomId}")
    ResponseEntity<RoomDetailedResponseDTO> modifyRoomViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody ModifyRoomDTO modifyRoomDTO) {
        validateRoomModificationPermissionsForManager(modifyRoomDTO);
        return ResponseEntity.ok(modifyRoom(memberId, roomId, modifyRoomDTO));
    }

    @ModifyRoomOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Modify an existing room for PubSub administrators")
    @PutMapping("/api/admin/rooms/{memberId}/{roomId}")
    ResponseEntity<RoomDetailedResponseDTO> modifyRoomAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody ModifyRoomDTO modifyRoomDTO) {
        return ResponseEntity.ok(modifyRoom(memberId, roomId, modifyRoomDTO));
    }

    @ModifyRoomOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Modify an existing room for PubSub managers")
    @PutMapping("/api/rooms/{roomId}")
    ResponseEntity<RoomDetailedResponseDTO> modifyRoomAsManager(
        @RoomIdParam @PathVariable String roomId,
        @Valid @RequestBody ModifyRoomDTO modifyRoomDTO) {
        var manager = userContext.getUsername();
        validateRoomModificationPermissionsForManager(modifyRoomDTO);
        return ResponseEntity.ok(modifyRoom(manager, roomId, modifyRoomDTO));
    }

    private static void validateRoomModificationPermissionsForManager(ModifyRoomDTO modifyRoomDTO) {
        if (modifyRoomDTO.getIdentifier() != null) {
            throw new BusinessViolationException(ROOM_IDENTIFIER_MODIFICATION_NOT_ALLOWED);
        } else if (modifyRoomDTO.getManagerIdentifier() != null) {
            throw new BusinessViolationException(ROOM_MANAGER_MODIFICATION_NOT_ALLOWED);
        }
    }

    private RoomDetailedResponseDTO modifyRoom(String manager, String room, ModifyRoomDTO modifyRoomDTO) {
        var response = modifyRoomUseCase.execute(modifyRoomDTO.toRequestWith(manager, room));
        return RoomDetailedResponseDTO.fromDomain(response.getRoom(), response.getManager());
    }

    @TerminateRoomOperation
    @Operation(summary = "Terminates an existing active room for PubSub managers accessing via XRoad network")
    @DeleteMapping("/rooms/{memberId}/{roomId}")
    ResponseEntity<Void> terminateRoomViaXRoad(@PathVariable String memberId, @PathVariable String roomId) {
        terminateRoomsUseCase.execute(TerminateRoomsUseCase.Request.of(memberId, roomId));
        return new ResponseEntity<>(NO_CONTENT);
    }

    @TerminateRoomOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Terminates an existing active room for PubSub administrators")
    @DeleteMapping("/api/admin/rooms/admin/{memberId}/{roomId}")
    ResponseEntity<Void> terminateRoomAsAdministrator(@PathVariable String memberId, @PathVariable String roomId) {
        terminateRoomsUseCase.execute(TerminateRoomsUseCase.Request.of(memberId, roomId));
        return new ResponseEntity<>(NO_CONTENT);
    }

    @TerminateRoomOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Terminates an existing active room for PubSub managers")
    @DeleteMapping("/api/rooms/{roomId}")
    ResponseEntity<Void> terminateRoomAsManager(@PathVariable String roomId) {
        var manager = userContext.getUsername();
        terminateRoomsUseCase.execute(TerminateRoomsUseCase.Request.of(manager, roomId));
        return new ResponseEntity<>(NO_CONTENT);
    }
}
