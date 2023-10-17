package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.paging.Page;
import com.govstack.information_mediator.pubsub.commons.paging.PageRequest;
import com.govstack.information_mediator.pubsub.domain.entity.id.PublisherID;
import com.govstack.information_mediator.pubsub.domain.entity.id.RoomID;
import com.govstack.information_mediator.pubsub.management.domain.port.FetchEventsViewPort;
import com.govstack.information_mediator.pubsub.management.domain.view.EventView;
import lombok.RequiredArgsConstructor;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.SortField;
import org.jooq.tools.StringUtils;
import org.springframework.stereotype.Service;

import java.time.Instant;

import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.*;
import static com.govstack.information_mediator.pubsub.shared.jooq.tables.Rooms.ROOMS;

@Service
@RequiredArgsConstructor
class EventsViewGateway implements FetchEventsViewPort {

    private final DSLContext dsl;

    @Override
    public Page<EventView> fetchEvents(RoomID roomID, Instant fromDate, Instant toDate, PageRequest pageRequest,
                                       String publisherIdentifier, String eventTypeIdentifier,
                                       Integer eventTypeVersion) {
        var condition = EVENTS.ROOM_ID.eq(roomID.getId())
            .and(EVENTS.CREATED_AT.greaterOrEqual(fromDate))
            .and(EVENTS.CREATED_AT.lessThan(toDate));
        condition = appendFilteringTerms(condition, publisherIdentifier, eventTypeIdentifier, eventTypeVersion);
        return fetchEventsOnCondition(condition, pageRequest);
    }

    @Override
    public Page<EventView> fetchEvents(RoomID roomID, PublisherID publisherID, Instant fromDate, Instant toDate,
                                       PageRequest pageRequest, String eventTypeIdentifier,
                                       Integer eventTypeVersion) {
        var condition = EVENTS.ROOM_ID.eq(roomID.getId())
            .and(EVENTS.PUBLISHER_ID.eq(publisherID.getId()))
            .and(EVENTS.CREATED_AT.greaterOrEqual(fromDate))
            .and(EVENTS.CREATED_AT.lessThan(toDate));
        condition = appendFilteringTerms(condition, null, eventTypeIdentifier, eventTypeVersion);
        return fetchEventsOnCondition(condition, pageRequest);
    }

    private Page<EventView> fetchEventsOnCondition(Condition condition, PageRequest pageRequest) {
        int totalNumberOfElements = dsl.fetchCount(
            dsl.select().from(EVENTS)
                .join(ROOMS).on(ROOMS.ID.eq(EVENTS.ROOM_ID))
                .join(PUBLISHERS).on(PUBLISHERS.ID.eq(EVENTS.PUBLISHER_ID).and(PUBLISHERS.ROOM_ID.eq(ROOMS.ID)))
                .join(EVENT_TYPES).on(EVENT_TYPES.ID.eq(EVENTS.EVENT_TYPE_ID))
                .join(EVENT_TYPE_VERSIONS).on(EVENT_TYPE_VERSIONS.ID.eq(EVENTS.EVENT_TYPE_VERSION_ID))
                .where(condition));

        var events = dsl.select(EVENTS.ID, EVENTS.CORRELATION_ID, EVENTS.CREATED_AT, PUBLISHERS.ID,
                PUBLISHERS.IDENTIFIER, EVENT_TYPES.IDENTIFIER, EVENT_TYPE_VERSIONS.VERSION)
            .from(EVENTS)
            .join(ROOMS).on(ROOMS.ID.eq(EVENTS.ROOM_ID))
            .join(PUBLISHERS).on(PUBLISHERS.ID.eq(EVENTS.PUBLISHER_ID).and(PUBLISHERS.ROOM_ID.eq(ROOMS.ID)))
            .join(EVENT_TYPES).on(EVENT_TYPES.ID.eq(EVENTS.EVENT_TYPE_ID))
            .join(EVENT_TYPE_VERSIONS).on(EVENT_TYPE_VERSIONS.ID.eq(EVENTS.EVENT_TYPE_VERSION_ID))
            .where(condition)
            .orderBy(getOrderByTerms(pageRequest))
            .limit(pageRequest.getMaxItemsPerPage())
            .offset(pageRequest.getMaxItemsPerPage() * pageRequest.getPageOffset())
            .fetch()
            .map(eventData -> EventView.builder()
                .id(eventData.get(EVENTS.ID))
                .correlationId(eventData.get(EVENTS.CORRELATION_ID))
                .createdAt(eventData.get(EVENTS.CREATED_AT))
                .publisherId(eventData.get(PUBLISHERS.ID))
                .publisherIdentifier(eventData.get(PUBLISHERS.IDENTIFIER))
                .eventTypeIdentifier(eventData.get(EVENT_TYPES.IDENTIFIER))
                .eventTypeVersionNo(eventData.get(EVENT_TYPE_VERSIONS.VERSION))
                .build());

        return Page.<EventView>builder()
            .content(events)
            .totalNumberOfElements(totalNumberOfElements)
            .maxItemsPerPage(pageRequest.getMaxItemsPerPage())
            .currentPageNumber(pageRequest.getPageOffset())
            .currentPageNumberOfElements(events.size())
            .build();
    }

    private SortField<?> getOrderByTerms(PageRequest pageRequest) {

        if (StringUtils.isEmpty(pageRequest.getSortBy()) ||
            StringUtils.equals(pageRequest.getSortBy(), "createdAt") ||
            StringUtils.equals(pageRequest.getSortBy(), "id")) {
            return pageRequest.isDescendingOrder() ? EVENTS.ID.desc() : EVENTS.ID.asc();
        }
        return switch (pageRequest.getSortBy()) {
            case "publisherIdentifier" ->
                pageRequest.isDescendingOrder() ? PUBLISHERS.IDENTIFIER.desc() : PUBLISHERS.IDENTIFIER.asc();
            case "eventTypeIdentifier" ->
                pageRequest.isDescendingOrder() ? EVENT_TYPES.IDENTIFIER.desc() : EVENT_TYPES.IDENTIFIER.asc();
            case "eventTypeVersionNo" ->
                pageRequest.isDescendingOrder() ? EVENT_TYPE_VERSIONS.VERSION.desc() : EVENT_TYPE_VERSIONS.VERSION.asc();
            default -> pageRequest.isDescendingOrder() ? EVENTS.ID.desc() : EVENTS.ID.asc();
        };
    }

    private Condition appendFilteringTerms(Condition condition, String publisherIdentifier, String eventTypeIdentifier,
                                           Integer eventTypeVersion) {
        if (!StringUtils.isEmpty(publisherIdentifier)) {
            condition = condition.and(PUBLISHERS.IDENTIFIER.containsIgnoreCase(publisherIdentifier));
        }
        if (!StringUtils.isEmpty(eventTypeIdentifier)) {
            condition = condition.and(EVENT_TYPES.IDENTIFIER.containsIgnoreCase(eventTypeIdentifier));
        }
        if (eventTypeVersion != null) {
            condition = condition.and(EVENT_TYPE_VERSIONS.VERSION.eq(eventTypeVersion));
        }
        return condition;
    }

}
