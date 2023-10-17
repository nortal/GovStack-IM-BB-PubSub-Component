package com.govstack.information_mediator.pubsub.config

import org.aeonbits.owner.Config
import org.aeonbits.owner.Config.DefaultValue
import org.aeonbits.owner.Config.Key
import org.aeonbits.owner.Config.Sources

@Sources(["classpath:application.properties"])
interface TestConfig extends Config {

    @Key("ss1.url")
    String ss1URL()

    @Key("keycloak.url")
    String keycloakURL()

    @Key("messaging-api.url")
    String messagingApiUrl()

    @Key("management-api.url")
    String managementApiUrl()

    @Key("management-ui.url")
    String managementUiUrl()

    @Key("db.url")
    String dbUrl()

    @Key("db.username")
    String dbUsername()

    @Key("db.password")
    String dbPassword()

    @Key("wiremock.url")
    String wiremockUrl()

    @Key("wiremock.admin.url")
    String wiremockAdminUrl()

    @Key("debug.mode")
    @DefaultValue("false")
    Boolean debugMode()

    @Key("selenide.remote_url")
    String selenideRemoteUrl()

}
