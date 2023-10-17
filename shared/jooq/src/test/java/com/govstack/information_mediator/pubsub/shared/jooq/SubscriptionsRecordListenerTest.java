package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.shared.jooq.tables.records.SubscriptionsRecord;
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
import static com.govstack.information_mediator.pubsub.shared.jooq.SubscriptionsParametersTestDataUtil.jsonPushConfiguration;
import static org.assertj.core.api.Assertions.assertThatNoException;

class SubscriptionsRecordListenerTest {
    private static final Field<JSONB> PARAMETERS_FIELD = DSL.field("parameters", JSONB.class);

    @SuppressWarnings("rawtypes")
    private static final Collection<Class<? extends UpdatableRecordImpl>> OTHER_RECORDS = retrieveUpdatableRecord(SubscriptionsRecord.class);

    private final JsonService jsonService = new JsonService(getObjectMapper());
    private final SubscriptionsRecordService subscriptionsRecordService = new SubscriptionsRecordService(jsonService);
    private final SubscriptionsRecordListener subscriptionsRecordListener = new SubscriptionsRecordListener(subscriptionsRecordService);

    @Test
    void shouldValidateSubscriptionsRecordOnInsertStart() {
        // Given
        JSONB parameters = JSONB.jsonb(jsonPushConfiguration());
        Record record = new SubscriptionsRecord();
        record.set(PARAMETERS_FIELD, parameters);

        RecordContext ctx = new MockRecordContext(record);

        // When
        assertThatNoException().isThrownBy(() -> subscriptionsRecordListener.insertStart(ctx));
    }

    @Test
    void shouldValidateSubscriptionsRecordOnUpdateStart() {
        // Given
        JSONB parameters = JSONB.jsonb(jsonPushConfiguration());
        Record record = new SubscriptionsRecord();
        record.set(PARAMETERS_FIELD, parameters);

        RecordContext ctx = new MockRecordContext(record);

        // When
        assertThatNoException().isThrownBy(() -> subscriptionsRecordListener.updateStart(ctx));
    }

    @Test
    void shouldIgnoreOtherRecordsOnInsertStart() {
        OTHER_RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record ->
                assertThatNoException().isThrownBy(() -> {
                    RecordContext ctx = new MockRecordContext(record);
                    subscriptionsRecordListener.updateStart(ctx);
                })
            );
    }

    @Test
    void shouldIgnoreOtherRecordsOnUpdateStart() {
        OTHER_RECORDS.stream()
            .map(JooqTestUtil::newInstance)
            .forEach(record ->
                assertThatNoException().isThrownBy(() -> {
                    RecordContext ctx = new MockRecordContext(record);
                    subscriptionsRecordListener.updateStart(ctx);
                })
            );
    }
}
