package com.govstack.information_mediator.pubsub.management.web.member;

import com.govstack.information_mediator.pubsub.commons.oas.annotations.InternalErrorApiResponse;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.management.domain.usecase.member.ExportMembersUserCase;
import com.govstack.information_mediator.pubsub.management.domain.usecase.member.ListMembersUseCase;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingSortingParametersDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
@Tag(name = "Members", description = "API for querying X-Road members who have access to IM PubSub subsystem")
public class MembersController {

    private final ListMembersUseCase listMembersUseCase;
    private final ExportMembersUserCase exportMembersUserCase;

    @Operation(summary = "Retrieves page of members")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved members")
    @InternalErrorApiResponse
    @GetMapping(value = {"/api/admin/members", "/api/members"})
    ResponseEntity<PagedMembersDTO> retrieveMembersPage(
        @ModelAttribute PagingSortingParametersDTO pagingSortingParameters
    ) {
        var pageRequest = PageRequest.builder()
            .descendingOrder(Boolean.TRUE.equals(pagingSortingParameters.getDesc()))
            .maxItemsPerPage(pagingSortingParameters.getLimit())
            .pageOffset(pagingSortingParameters.getOffset())
            .sortBy(pagingSortingParameters.getSortBy())
            .filterQuery(pagingSortingParameters.getFilterBy())
            .build();

        var request = ListMembersUseCase.Request.builder()
            .pageRequest(pageRequest)
            .build();

        return ResponseEntity.ok(
            PagedMembersDTO.from(listMembersUseCase.execute(request).members())
        );
    }

    @Operation(summary = "Retrieves members")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved members")
    @InternalErrorApiResponse
    @GetMapping(value = {"/api/admin/export/members", "/api/export/members"})
    ResponseEntity<List<MemberDTO>> exportMembers() {
        var members = exportMembersUserCase.execute().members()
            .stream()
            .map(MemberDTO::fromDomain)
            .toList();
        return ResponseEntity.ok(members);
    }
}
