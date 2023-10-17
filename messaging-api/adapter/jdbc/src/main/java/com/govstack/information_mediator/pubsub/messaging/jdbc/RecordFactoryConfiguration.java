package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;

@ApplicationScoped
@RequiredArgsConstructor
class RecordFactoryConfiguration {

    private final DSLContext dslContext;

    @Produces
    @Dependent
    @SuppressWarnings("rawtypes")
    public <T extends UpdatableRecord> RecordFactory<T> recordFactory() {
        return new RecordFactory<>(dslContext);
    }
}
