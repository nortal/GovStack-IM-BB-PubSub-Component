package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType;
import com.govstack.information_mediator.pubsub.domain.entity.Publisher;
import com.govstack.information_mediator.pubsub.domain.entity.Room;
import com.govstack.information_mediator.pubsub.messaging.domain.port.FetchPublishersPort;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.PublishersRecord;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.EnumUtils;

import java.util.Optional;

import static com.govstack.information_mediator.pubsub.shared.jooq.tables.Publishers.PUBLISHERS;

@ApplicationScoped
@RequiredArgsConstructor
class PublishersGateway implements FetchPublishersPort {

    private final RecordFactory<PublishersRecord> publishersRecordFactory;

    @Override
    @Transactional
    public Optional<Publisher> fetchPublisher(Room room, String identifier) {
        var condition = PUBLISHERS.IDENTIFIER.eq(identifier).and(PUBLISHERS.ROOM_ID.eq(room.getId()));
        return publishersRecordFactory.loadUsingCondition(PublishersRecord.class, condition)
            .map(this::toDomainEntity);
    }

    private Publisher toDomainEntity(PublishersRecord record) {
        return Publisher.builder()
            .id(record.getId())
            .roomId(record.getRoomId())
            .identifier(record.getIdentifier())
            .identifierType(EnumUtils.getEnum(IdentifierType.class, record.getIdentifierType()))
            .build();
    }
}
