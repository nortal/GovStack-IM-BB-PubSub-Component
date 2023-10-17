package com.govstack.information_mediator.pubsub.messaging.jdbc;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.shared.jooq.AuditInformation;
import io.quarkiverse.jooq.runtime.JooqCustomContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jooq.Configuration;
import org.jooq.conf.RenderNameCase;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;

import java.time.Clock;

import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createIdRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createJournalRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createRoomsRecordListener;
import static com.govstack.information_mediator.pubsub.shared.jooq.RecordListenerProviderFactory.createSubscriptionsRecordListener;

@ApplicationScoped
public class DatabaseConfiguration {

    @Inject
    AuditInformation auditInformation;

    @Inject
    Clock clock;

    @Inject
    JsonService jsonService;

    @Produces
    @ApplicationScoped
    @Named("jooqDataSourceConfiguration")
    public JooqCustomContext create() {

        return new JooqCustomContext() {
            @Override
            public void apply(Configuration configuration) {
                Settings settings = configuration.settings();
                settings.withRenderQuotedNames(RenderQuotedNames.EXPLICIT_DEFAULT_UNQUOTED);
                settings.withRenderNameCase(RenderNameCase.LOWER_IF_UNQUOTED);

                configuration.set(
                    createIdRecordListener(),
                    createJournalRecordListener(auditInformation, clock, jsonService),
                    createRoomsRecordListener(jsonService),
                    createSubscriptionsRecordListener(jsonService)
                );
            }
        };
    }

}
