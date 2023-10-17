package com.govstack.information_mediator.pubsub.management.web.subscription;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
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
@Schema(description = "Paginated subscriptions list", name = "PaginatedSubscriptionsList")
public class PagedSubscriptionResponseDTO {

    @Schema(description = "List of subscriptions", requiredMode = REQUIRED)
    List<SubscriptionResponseDTO> subscriptions = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedSubscriptionResponseDTO from(Page<SubscriptionView> subscriptionPage) {
        return PagedSubscriptionResponseDTO.builder()
            .subscriptions(
                subscriptionPage.getContent().stream()
                    .map(SubscriptionResponseDTO::fromView)
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(subscriptionPage.getCurrentPageNumberOfElements())
                    .limit(subscriptionPage.getMaxItemsPerPage())
                    .totalItems(subscriptionPage.getTotalNumberOfElements())
                    .offset(subscriptionPage.getCurrentPageNumber())
                    .build())
            .build();
    }

}
