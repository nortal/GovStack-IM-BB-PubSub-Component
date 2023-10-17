package com.govstack.information_mediator.pubsub.ui

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType
import com.govstack.information_mediator.pubsub.steps.KeyCloakPage
import com.govstack.information_mediator.pubsub.steps.management_ui.LoginPage
import com.govstack.information_mediator.pubsub.steps.management_ui.RoomListPage
import com.govstack.information_mediator.pubsub.steps.management_ui.common.Alert
import com.govstack.information_mediator.pubsub.steps.management_ui.common.HeaderBar
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Issue
import io.qameta.allure.Story
import spock.lang.Tag

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible

@Tag("PubSub")
@Tag("UI")
@Epic("Management-ui")
@Feature("Login/logout")
class LoginTests extends GenericSpecification {

    @Issue("M604017-90")
    @UseCase("UC2.1")
    @Story("UC2.1 - Log into management-ui with OAuth2")
    def "Given: management-ui is opened; When: #desc: #user logs in; Then: Rooms List page is shown"() {
        given: "management-ui is opened"
        if (desc == "management user that exists in PubSub") {
            db.addManager(user, IdentifierType.INTERNAL, true)
        }
        LoginPage.open()

        when: "login into KeyCloak"
        LoginPage.logInWithOauth2()
        KeyCloakPage.logIn(user, password)

        then: "Rooms List page is shown"
        RoomListPage.title.shouldBe(text("Rooms List"))
        if (desc == "admin user") {
            HeaderBar.menu.shouldBe(visible)
        }
        Alert.alertBox.shouldNotBe(visible)

        cleanup: "log out of management-ui"
        HeaderBar.logOut()
        LoginPage.title.shouldBe(visible)

        where:
        desc                                    | user                    | password
        "admin user"                            | "system-administrator"  | "system-administrator"
        "admin user"                            | "service-administrator" | "service-administrator"
        "management user that exists in PubSub" | "internal-manager"      | "pass"
//        "management user that doesn't exist in PubSub" | "EE/BUSINESS/111111111" | "pass"
    }

    @UseCase("UC2.2")
    @Story("UC2.2 - Log out of management-ui")
    def "Given: user logged into management-ui; When: user logs out; Then: user redirected to management-ui login page"() {
        given: "user logged into management-ui"
        LoginPage.open()
        LoginPage.logInWithOauth2()
        KeyCloakPage.logIn()
        RoomListPage.title.shouldBe(visible)

        when: "user logs out"
        HeaderBar.logOut()

        then: "user redirected to management-ui login page"
        LoginPage.title.shouldBe(visible)
    }

}
