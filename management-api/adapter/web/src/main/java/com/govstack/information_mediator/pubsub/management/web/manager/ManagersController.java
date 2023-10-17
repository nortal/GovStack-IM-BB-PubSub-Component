package com.govstack.information_mediator.pubsub.management.web.manager;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.InternalErrorApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ManagerAlreadyExistsApiResponse;
import com.govstack.information_mediator.pubsub.commons.oas.annotations.ManagerNotFoundApiResponse;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.CreateManagersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.ExportManagersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.RetrievePagedManagersUseCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.manager.TerminateManagersUseCase;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SERVICE_ADMINISTRATOR;
import static com.govstack.information_mediator.pubsub.management.domain.auth.ManagementRoles.XROAD_SYSTEM_ADMINISTRATOR;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Managers", description = "API for managing managers")
class ManagersController {

    private final ExportManagersUseCase exportManagersUseCase;
    private final RetrievePagedManagersUseCase retrievePagedManagersUseCase;
    private final CreateManagersUseCase createManagersUseCase;
    private final TerminateManagersUseCase terminateManagersUseCase;

    @Operation(summary = "Retrieves all managers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all managers")
    @InternalErrorApiResponse
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @GetMapping(value = "/api/admin/export/managers")
    ResponseEntity<List<ManagerResponseDTO>> retrieveAllManagers() {
        return ResponseEntity.ok(exportManagersUseCase.execute()
            .managers()
            .stream()
            .map(ManagerResponseDTO::fromDomain)
            .toList());
    }

    @Operation(summary = "Retrieves page of managers")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all managers")
    @InternalErrorApiResponse
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @GetMapping(value = "/api/admin/managers")
    ResponseEntity<PagedManagersResponseDTO> retrieveManagers(
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters) {
        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(pagingSortingParameters.getDesc()))
            .maxItemsPerPage(pagingSortingParameters.getLimit())
            .pageOffset(pagingSortingParameters.getOffset())
            .sortBy(pagingSortingParameters.getSortBy())
            .filterQuery(pagingSortingParameters.getFilterBy())
            .build();
        var response = retrievePagedManagersUseCase.execute(new RetrievePagedManagersUseCase.Request(pageRequest));
        return ResponseEntity.ok(PagedManagersResponseDTO.from(response.managers()));
    }

    @Operation(summary = "Creates a manager")
    @ApiResponse(responseCode = "200", description = "Successfully created a manager")
    @InternalErrorApiResponse
    @ManagerAlreadyExistsApiResponse
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @PostMapping(value = "/api/admin/managers")
    ResponseEntity<ManagerResponseDTO> createManager(@Valid @RequestBody CreateManagerDTO createManagerDTO) {
        var response = createManagersUseCase.execute(createManagerDTO.toRequest());
        return ResponseEntity.ok(ManagerResponseDTO.fromDomain(response.manager()));
    }

    @Operation(summary = "Terminates a manager")
    @ApiResponse(responseCode = "204", description = "Successfully terminated manager")
    @ManagerNotFoundApiResponse
    @InternalErrorApiResponse
    @Secured({ XROAD_SYSTEM_ADMINISTRATOR, XROAD_SERVICE_ADMINISTRATOR })
    @DeleteMapping(value = "/api/admin/managers/{managerId}")
    ResponseEntity<Void> terminateManager(
        @Parameter(description = "The manager id") @PathVariable UUID managerId) {
        terminateManagersUseCase.execute(new TerminateManagersUseCase.Request(managerId));
        return new ResponseEntity<>(NO_CONTENT);
    }
}
