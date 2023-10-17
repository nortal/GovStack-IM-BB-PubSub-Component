package com.govstack.information_mediator.pubsub.management.web.publisher;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.management.domain.view.PublisherView;
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
@Schema(description = "Paginated publishers response", name = "PaginatedPublishersResponse")
public class PagedPublishersViewDTO {

    @Schema(description = "List of publishers", requiredMode = REQUIRED)
    List<PublisherViewDTO> publishers = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedPublishersViewDTO from(Page<PublisherView> publisherPage) {
        return PagedPublishersViewDTO.builder()
            .publishers(
                publisherPage.getContent()
                    .stream()
                    .map(PublisherViewDTO::fromDomain)
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(publisherPage.getCurrentPageNumberOfElements())
                    .limit(publisherPage.getMaxItemsPerPage())
                    .totalItems(publisherPage.getTotalNumberOfElements())
                    .offset(publisherPage.getCurrentPageNumber())
                    .build())
            .build();
    }
}
