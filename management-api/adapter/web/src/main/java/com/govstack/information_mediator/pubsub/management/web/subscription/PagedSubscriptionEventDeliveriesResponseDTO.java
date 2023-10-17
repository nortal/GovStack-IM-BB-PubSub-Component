package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionEventDeliveryView;
import com.govstack.information_mediator.pubsub.domain.view.SubscriptionView;
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
@Schema(description = "Paginated subscription events delivery list", name = "PaginatedSubscriptionEventDeliveriesList")
public class PagedSubscriptionEventDeliveriesResponseDTO {

    @Schema(description = "List of events with delivery info", requiredMode = REQUIRED)
    List<SubscriptionEventDeliveryResponseDTO> events = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedSubscriptionEventDeliveriesResponseDTO from(Page<SubscriptionEventDeliveryView> eventDeliveriesPage) {
        return PagedSubscriptionEventDeliveriesResponseDTO.builder()
            .events(
                eventDeliveriesPage.getContent().stream()
                    .map(SubscriptionEventDeliveryResponseDTO::fromView)
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(eventDeliveriesPage.getCurrentPageNumberOfElements())
                    .limit(eventDeliveriesPage.getMaxItemsPerPage())
                    .totalItems(eventDeliveriesPage.getTotalNumberOfElements())
                    .offset(eventDeliveriesPage.getCurrentPageNumber())
                    .build())
            .build();
    }

}
