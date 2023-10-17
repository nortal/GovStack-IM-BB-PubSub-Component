package com.govstack.information_mediator.pubsub.shared.jooq;

class MockAuditInformation implements AuditInformation {

    private String name = "SYSTEM";

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
