package com.govstack.information_mediator.pubsub.management.web.manager;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.entity.Manager;
import com.govstack.information_mediator.pubsub.management.web.paging.PagingMetadataDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import static io.swagger.v3.oas.annotations.media.Schema.RequiredMode.REQUIRED;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Paged managers response", name = "PagedManagersResponse")
public class PagedManagersResponseDTO {

    @Schema(description = "List of managers", requiredMode = REQUIRED)
    List<ManagerResponseDTO> managers = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedManagersResponseDTO from(Page<Manager> managerPage) {
        return PagedManagersResponseDTO.builder()
            .managers(
                managerPage.getContent()
                    .stream()
                    .map(ManagerResponseDTO::fromDomain)
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(managerPage.getCurrentPageNumberOfElements())
                    .limit(managerPage.getMaxItemsPerPage())
                    .totalItems(managerPage.getTotalNumberOfElements())
                    .offset(managerPage.getCurrentPageNumber())
                    .build())
            .build();
    }
}
