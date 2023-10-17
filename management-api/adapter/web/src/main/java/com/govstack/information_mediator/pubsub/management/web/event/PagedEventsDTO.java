package com.govstack.information_mediator.pubsub.management.web.event;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.management.domain.view.EventView;
import com.govstack.information_mediator.pubsub.management.web.member.MemberDTO;
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
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "EventsViewResponse", description = "Event view response with paging metadata")
public class PagedEventsDTO {

    @Schema(description = "List of eventsPage", requiredMode = REQUIRED)
    List<EventViewDTO> events = null;

    @Schema(description = "Paging metadata", requiredMode = REQUIRED)
    PagingMetadataDTO pagingMetadata = null;

    static PagedEventsDTO from (Page<EventView> eventsPage) {
        return PagedEventsDTO.builder()
            .events(
                eventsPage.getContent().stream()
                    .map(event -> EventViewDTO.builder()
                        .id(event.getId())
                        .correlationId(event.getCorrelationId())
                        .createdAt(event.getCreatedAt())
                        .publisherId(event.getPublisherId())
                        .publisherIdentifier(event.getPublisherIdentifier())
                        .eventTypeIdentifier(event.getEventTypeIdentifier())
                        .eventTypeVersionNo(event.getEventTypeVersionNo())
                        .build())
                    .toList())
            .pagingMetadata(
                PagingMetadataDTO.builder()
                    .items(eventsPage.getCurrentPageNumberOfElements())
                    .limit(eventsPage.getMaxItemsPerPage())
                    .totalItems(eventsPage.getTotalNumberOfElements())
                    .offset(eventsPage.getCurrentPageNumber())
                    .build())
            .build();
    }
}
