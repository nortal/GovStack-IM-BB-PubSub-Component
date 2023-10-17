package com.govstack.information_mediator.pubsub.steps

import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

class KeyCloakPage {

    static String baseUrl = ConfigHolder.getConf().keycloakURL()

    static SelenideElement pageHeader = $("#kc-header-wrapper")
    static SelenideElement pageTitle = $("#kc-page-title")
    static SelenideElement userNameField = $("#username")
    static SelenideElement passwordField = $("#password")
    static SelenideElement logInButton = $("#kc-login")
    static SelenideElement logOutButton = $("#kc-logout")

    @Step("Log into KeyCloak")
    static void logIn(String username = "system-administrator", String password = username) {
        pageTitle.shouldBe(visible)
        userNameField.sendKeys(username)
        passwordField.sendKeys(password)
        logInButton.click()
    }

    @Step("Log out of KeyCloak")
    static void logOut() {
        open("${baseUrl}/realms/pubsub-realm/protocol/openid-connect/logout")
        pageTitle.shouldBe(text("Logging out"))
        logOutButton.click()
    }

}
