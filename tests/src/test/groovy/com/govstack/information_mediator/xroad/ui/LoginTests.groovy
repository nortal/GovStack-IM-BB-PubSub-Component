package com.govstack.information_mediator.xroad.ui

import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.steps.KeyCloakPage
import com.govstack.information_mediator.pubsub.steps.SecurityServerLoginPage
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import spock.lang.Tag

import static com.codeborne.selenide.Condition.text

@Tag("XRoad")
@Tag("UI")
@Epic("XRoad")
@Feature("Login")
class LoginTests extends GenericSpecification {

    @Story("Log into Security server with OAuth2")
    def "Given: security server is opened; When: user #username tries to log in; Then: user is logged in"() {
        given: "security server is opened"
        SecurityServerLoginPage.open()

        when: "user tries to log into SS with OAuth2"
        SecurityServerLoginPage.logIn()
        KeyCloakPage.logIn(username, password)

        then: "user is logged"
        SecurityServerLoginPage.usernameButton.shouldBe(text(username))

        cleanup: "log out of SS and KeyCloak"
        SecurityServerLoginPage.logOut()
        KeyCloakPage.logOut()

        where:
        username                  | password
        "security-officer"        | "security-officer"
        "registration-officer"    | "registration-officer"
        "system-administrator"    | "system-administrator"
        "securityserver-observer" | "securityserver-observer"
        "service-administrator"   | "service-administrator"
    }

}
