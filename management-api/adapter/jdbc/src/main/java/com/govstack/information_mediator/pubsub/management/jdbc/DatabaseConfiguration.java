package com.govstack.information_mediator.pubsub.management.jdbc;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.shared.jooq.AuditInformation;
import com.govstack.information_mediator.pubsub.shared.jooq.RecordFactory;
import org.jooq.DSLContext;
import org.jooq.UpdatableRecord;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;

import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createIdRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createJournalRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createRoomsRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createSubscriptionsRecordListener;

@Configuration
class DatabaseConfiguration {

    @Bean
    @SuppressWarnings("rawtypes")
    public <T extends UpdatableRecord> RecordFactory<T> recordFactory(DSLContext dsl) {
        return new RecordFactory<>(dsl);
    }

    @Bean
    public DefaultConfigurationCustomizer jooqConfigurationCustomizer(AuditInformation auditInformation,
                                                                      Clock clock, JsonService jsonService) {

        return configuration -> {
            var settings = configuration.settings();
            settings.withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED);
            settings.withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED);

            configuration.set(
                createIdRecordListener(),
                createJournalRecordListener(auditInformation, clock, jsonService),
                createRoomsRecordListener(jsonService),
                createSubscriptionsRecordListener(jsonService)
            );
        };
    }
}
