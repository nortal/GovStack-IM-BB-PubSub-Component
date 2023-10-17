package com.govstack.information_mediator.pubsub.steps

import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

class SecurityServerLoginPage {

    static String baseUrl = ConfigHolder.getConf().ss1URL()

    static SelenideElement title = $(".sub-title")
    static SelenideElement loginButton = $("#oauth-login-button")
    static SelenideElement usernameButton = $("[data-test='username-button']")
    static SelenideElement logoutMenuItem = $("#logout-title")

    @Step("Open {baseUrl}")
    static void open() {
        open(baseUrl)
        title.shouldBe(text("X-Road Security Server"))
    }

    @Step("Log into Security Server")
    static void logIn() {
        loginButton.click()
        KeyCloakPage.pageHeader.shouldBe(text("pubsub-realm"))
    }

    @Step("Log out of Security Server")
    static void logOut() {
        usernameButton.click()
        logoutMenuItem.click()
        title.shouldBe(text("X-Road Security Server"))
    }

}
