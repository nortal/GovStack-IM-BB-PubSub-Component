package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.RoomsRecord;
import org.jooq.Field;
import org.jooq.JSONB;
import org.jooq.Record;
import org.jooq.RecordContext;
import org.jooq.impl.DSL;
import org.jooq.impl.UpdatableRecordImpl;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static com.govstack.information_mediator.pubsub.commons.ObjectMapperFactory.getObjectMapper;
import static com.govstack.information_mediator.pubsub.shared.jooq.JooqTestUtil.retrieveUpdatableRecord;
import static com.govstack.information_mediator.pubsub.shared.jooq.RoomsConfigurationTestDataUtil.jsonRoomConfiguration;
import static org.assertj.core.api.Assertions.assertThatNoException;

class RoomsConfigurationRecordListenerTest {
    private static final Field<JSONB> CONFIGURATION_FIELD = DSL.field("configuration", JSONB.class);

    @SuppressWarnings("rawtypes")
    private static final Collection<Class<? extends UpdatableRecordImpl>> OTHER_RECORDS = retrieveUpdatableRecord(RoomsRecord.class);

    private final JsonService jsonService = new JsonService(getObjectMapper());
    private final RoomsRecordService roomsRecordService = new RoomsRecordService(jsonService);
    private final RoomsRecordListener roomsRecordListener = new RoomsRecordListener(roomsRecordService);

    @Test
    void shouldValidateConfigurationOnInsertStart() {
        // Given
        JSONB configuration = JSONB.jsonb(jsonRoomConfiguration());
        Record record = new RoomsRecord();
        record.set(CONFIGURATION_FIELD, configuration);

        RecordContext ctx = new MockRecordContext(record);

        // When
        assertThatNoException().isThrownBy(() -> roomsRecordListener.insertStart(ctx));
    }

    @Test
    void shouldValidateConfigurationOnUpdateStart() {
        // Given
        JSONB configuration = JSONB.jsonb(jsonRoomConfiguration());
        Record record = new RoomsRecord();
        record.set(CONFIGURATION_FIELD, configuration);

        RecordContext ctx = new MockRecordContext(record);

        // When
        assertThatNoException().isThrownBy(() -> roomsRecordListener.updateStart(ctx));
    }

    @Test
    void shouldIgnoreOtherRecordsOnInsertStart() {
        OTHER_RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record ->{
                RecordContext ctx = new MockRecordContext(record);
                assertThatNoException().isThrownBy(() -> roomsRecordListener.updateStart(ctx));
            });
    }

    @Test
    void shouldIgnoreOtherRecordsOnUpdateStart() {
        OTHER_RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record -> {
                RecordContext ctx = new MockRecordContext(record);
                assertThatNoException().isThrownBy(() -> roomsRecordListener.updateStart(ctx));
            });
    }
}
