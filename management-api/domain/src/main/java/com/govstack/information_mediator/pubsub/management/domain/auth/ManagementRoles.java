package com.govstack.information_mediator.pubsub.management.domain.auth;

public class ManagementRoles {
    public static final String PUBSUB_MANAGER = "ROLE_PUBSUB-MANAGER";
    public static final String XROAD_SYSTEM_ADMINISTRATOR = "ROLE_XROAD-SYSTEM-ADMINISTRATOR";
    public static final String XROAD_SERVICE_ADMINISTRATOR = "ROLE_XROAD-SERVICE-ADMINISTRATOR";

    private ManagementRoles() {
        throw new AssertionError("Static management roles class should not be instantiated");
    }
}
