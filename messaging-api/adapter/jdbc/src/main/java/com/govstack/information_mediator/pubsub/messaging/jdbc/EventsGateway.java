package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.Event;
import com.govstack.information_mediator.pubsub.messaging.domain.port.EventsPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.EventsRecord;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;

import java.util.UUID;

import static com.govstack.information_mediator.pubsub.shared.jooq.Tables.EVENTS;

@ApplicationScoped
@RequiredArgsConstructor
class EventsGateway implements EventsPort {

    private final DSLContext dsl;
    private final RecordFactory<EventsRecord> eventsRecordFactory;

    @Override
    public UUID createEvent(Event event) {
        var record = eventsRecordFactory.loadOrCreate(EventsRecord.class, null);
        record.setRoomId(event.getRoomId());
        record.setEventTypeId(event.getEventTypeId());
        record.setEventTypeVersionId(event.getEventTypeVersionId());
        record.setPublisherId(event.getPublisherId());
        record.setCorrelationId(event.getCorrelationId());
        record.setCreatedAt(event.getCreatedAt());
        record.store();

        return record.getId();
    }

    @Override
    public boolean containsEvent(UUID eventId) {
        return dsl.fetchCount(EVENTS, EVENTS.ID.eq(eventId)) != 0;
    }
}
