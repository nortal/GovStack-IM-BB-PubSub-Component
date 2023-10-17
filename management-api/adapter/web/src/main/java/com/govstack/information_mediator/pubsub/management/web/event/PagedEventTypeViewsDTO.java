package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.management.domain.view.EventTypeView;
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
@Schema(description = "Paginated event types response", name = "PagedEventTypeViewsResponse")
public class PagedEventTypeViewsDTO {

    @Schema(description = "List of event types", requiredMode = REQUIRED)
    List<EventTypeViewDTO> eventTypes;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata;

    static PagedEventTypeViewsDTO from(Page<EventTypeView> page) {
        return PagedEventTypeViewsDTO.builder()
            .eventTypes(page.getContent()
                .stream()
                .map(EventTypeViewDTO::fromDomain)
                .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(page.getCurrentPageNumberOfElements())
                    .limit(page.getMaxItemsPerPage())
                    .totalItems(page.getTotalNumberOfElements())
                    .offset(page.getCurrentPageNumber())
                    .build())
            .build();
    }
}
