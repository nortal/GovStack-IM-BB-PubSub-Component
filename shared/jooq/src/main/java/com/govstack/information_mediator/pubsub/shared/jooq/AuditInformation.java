package com.govstack.information_mediator.pubsub.shared.jooq;

public interface AuditInformation {

    /**
     * Must return an auditable name or provide a default name
     *
     * @return auditable name, never null
     */
    String getName();
}
