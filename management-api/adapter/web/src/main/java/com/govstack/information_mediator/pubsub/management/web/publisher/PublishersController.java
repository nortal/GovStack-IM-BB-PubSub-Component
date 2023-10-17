package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.MemberIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.PublisherIdParam;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.RoomIdParam;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.ManagerIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.MemberIdentifier;
import com.govstack.information_mediator.pubsub.domain.entity.identifier.RoomIdentifier;
import com.govstack.information_mediator.pubsub.management.domain.auth.UserContext;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.CreatePublisherUseCae;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.GetPublishersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.TerminatePublisherUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.publisher.UpdatePublisherUseCase;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.CreatePublisherOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.GetPublishersOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.TerminatePublisherOperation;
import com.govstack.information_mediator.pubsub.management.web.oas.annotations.UpdatePublisherOperation;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.PUBSUB_MANAGER;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;
import static org.springframework.http.HttpStatus.OK;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Publishers", description = "API for managing publishers")
class PublishersController {

    private final UserContext userContext;
    private final GetPublishersUseCase getPublishersUseCase;
    private final TerminatePublisherUseCase terminatePublisherUseCase;
    private final UpdatePublisherUseCase updatePublisherUseCase;
    private final CreatePublisherUseCae createPublisherUseCae;

    @GetPublishersOperation
    @Operation(summary = "Lists all publishers in a message room for PubSub members accessing via XRoad network")
    @GetMapping("/rooms/{memberId}/{roomId}/publishers")
    public ResponseEntity<PagedPublishersViewDTO> getPublishersViaXroad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(
            getPublishers(
                new MemberIdentifier(memberId),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    @GetPublishersOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Lists all publishers in a message room for PubSub administrators")
    @GetMapping("/api/admin/rooms/{memberId}/{roomId}/publishers")
    public ResponseEntity<PagedPublishersViewDTO> getPublishersAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(
            getPublishers(
                new MemberIdentifier(memberId),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    @GetPublishersOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Lists all publishers in a message room for PubSub manager")
    @GetMapping("/api/rooms/{roomId}/publishers")
    public ResponseEntity<PagedPublishersViewDTO> getPublishersAsManager(
        @RoomIdParam @PathVariable String roomId,
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        return ResponseEntity.ok(
            getPublishers(
                new MemberIdentifier(userContext.getUsername()),
                new RoomIdentifier(roomId),
                pagingSortingParameters));
    }

    private PagedPublishersViewDTO getPublishers(
        MemberIdentifier member,
        RoomIdentifier room,
        PagingSortingParametersDTO params) {

        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(params.getDesc()))
            .maxItemsPerPage(params.getLimit())
            .pageOffset(params.getOffset())
            .sortBy(params.getSortBy())
            .filterQuery(params.getFilterBy())
            .build();

        var request = new GetPublishersUseCase.Request(member, room, pageRequest);
        return PagedPublishersViewDTO.from(getPublishersUseCase.execute(request).publishers());
    }

    @TerminatePublisherOperation
    @Operation(summary = "Terminate a publisher in a message room for a PubSub member accessing via XRoad network")
    @DeleteMapping("/rooms/{memberId}/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> terminatePublisherViaXRoad(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @PublisherIdParam @PathVariable UUID publisherId) {
        terminatePublisher(
            new ManagerIdentifier(memberId),
            new RoomIdentifier(roomId),
            new PublisherID(publisherId));
        return new ResponseEntity<>(OK);
    }

    @TerminatePublisherOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Terminate a publisher in a message room for a PubSub administrator")
    @DeleteMapping("/api/admin/rooms/{memberId}/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> terminatePublisherAsAdministrator(
        @MemberIdParam @PathVariable String memberId,
        @RoomIdParam @PathVariable String roomId,
        @PublisherIdParam @PathVariable UUID publisherId) {
        terminatePublisher(
            new ManagerIdentifier(memberId),
            new RoomIdentifier(roomId),
            new PublisherID(publisherId));
        return new ResponseEntity<>(OK);
    }

    @TerminatePublisherOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Terminate a publisher in a message room for a PubSub manager")
    @DeleteMapping("/api/rooms/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> terminatePublisherAsManager(
        @RoomIdParam @PathVariable String roomId,
        @PublisherIdParam @PathVariable UUID publisherId) {
        terminatePublisher(
            new ManagerIdentifier(userContext.getUsername()),
            new RoomIdentifier(roomId),
            new PublisherID(publisherId));
        return new ResponseEntity<>(OK);
    }

    private void terminatePublisher(ManagerIdentifier manager, RoomIdentifier room, PublisherID publisher) {
        terminatePublisherUseCase.execute(new TerminatePublisherUseCase.Request(manager, room, publisher));
    }

    @UpdatePublisherOperation
    @Operation(summary = "Update publisher's constraints in a message room for a PubSub member accessing via XRoad network")
    @PutMapping("/rooms/{memberId}/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> updatePublisherViaXRoad(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @PublisherIdParam @PathVariable UUID publisherId,
            @RequestBody UpdatePublisherDTO updatePublisherDTO) {
        updatePublisher(
                new ManagerIdentifier(memberId),
                new RoomIdentifier(roomId),
                new PublisherID(publisherId),
                updatePublisherDTO);
        return new ResponseEntity<>(OK);
    }

    @UpdatePublisherOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Update publisher's constraints in a message room for a PubSub administrator")
    @PutMapping("/api/admin/rooms/{memberId}/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> updatePublisherAsAdministrator(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @PublisherIdParam @PathVariable UUID publisherId,
            @RequestBody UpdatePublisherDTO updatePublisherDTO) {
        updatePublisher(
                new ManagerIdentifier(memberId),
                new RoomIdentifier(roomId),
                new PublisherID(publisherId),
                updatePublisherDTO);
        return new ResponseEntity<>(OK);
    }

    @UpdatePublisherOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Update publisher's constraints in a message room for a PubSub manager")
    @PutMapping("/api/rooms/{roomId}/publishers/{publisherId}")
    public ResponseEntity<Void> updatePublisherAsManager(
            @RoomIdParam @PathVariable String roomId,
            @PublisherIdParam @PathVariable UUID publisherId,
            @RequestBody UpdatePublisherDTO updatePublisherDTO) {
        updatePublisher(
                new ManagerIdentifier(userContext.getUsername()),
                new RoomIdentifier(roomId),
                new PublisherID(publisherId),
                updatePublisherDTO);
        return new ResponseEntity<>(OK);
    }

    private void updatePublisher(ManagerIdentifier manager, RoomIdentifier room, PublisherID publisher, UpdatePublisherDTO dto) {
        var request = new UpdatePublisherUseCase.Request(manager, room, publisher, dto.getEventTypes());
        updatePublisherUseCase.execute(request);
    }

    @CreatePublisherOperation
    @Operation(summary = "Create a publisher in a message room for a PubSub member accessing via XRoad network")
    @PostMapping("/rooms/{memberId}/{roomId}/publishers")
    public ResponseEntity<CreatePublisherResponseDTO> createPublisherViaXRoad(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @RequestBody CreatePublisherDTO createPublisherDTO) {
        return ResponseEntity.ok(createPublisher(new ManagerIdentifier(memberId), new RoomIdentifier(roomId), createPublisherDTO));
    }

    @CreatePublisherOperation
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @Operation(summary = "Create a publisher in a message room for a PubSub administrator")
    @PostMapping("/api/admin/rooms/{memberId}/{roomId}/publishers")
    public ResponseEntity<CreatePublisherResponseDTO> createPublisherAsAdministrator(
            @MemberIdParam @PathVariable String memberId,
            @RoomIdParam @PathVariable String roomId,
            @RequestBody CreatePublisherDTO createPublisherDTO) {
        return ResponseEntity.ok(createPublisher(new ManagerIdentifier(memberId), new RoomIdentifier(roomId), createPublisherDTO));
    }

    @CreatePublisherOperation
    @Secured(PUBSUB_MANAGER)
    @Operation(summary = "Create a publisher in a message room for a PubSub manager")
    @PostMapping("/api/rooms/{roomId}/publishers")
    public ResponseEntity<CreatePublisherResponseDTO> createPublisherAsManager(
            @RoomIdParam @PathVariable String roomId,
            @RequestBody CreatePublisherDTO createPublisherDTO) {
        return ResponseEntity.ok(createPublisher(new ManagerIdentifier(userContext.getUsername()), new RoomIdentifier(roomId), createPublisherDTO));
    }

    private CreatePublisherResponseDTO createPublisher(ManagerIdentifier manager, RoomIdentifier room, CreatePublisherDTO dto) {
        var response = createPublisherUseCae.execute(dto.toRequest(manager, room));
        return CreatePublisherResponseDTO.fromDomain(response.publisherID());
    }
}
