package com.govstack.information_mediator.pubsub.steps.management_ui

import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.config.ConfigHolder
import io.qameta.allure.Step

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.open

class LoginPage {

    static String baseUrl = ConfigHolder.getConf().managementUiUrl()

    static SelenideElement logInButton = $("#oauth-login-button")
    static SelenideElement title = $(".login-form-title")
    static SelenideElement subTitle = $(".sub-title")

    @Step("Open {baseUrl}")
    static void open() {
        open(baseUrl)
        title.shouldBe(text("Log in"))
        subTitle.shouldBe(text("IM-BB PubSub Management"))
    }

    @Step("Log into management-ui")
    static void logInWithOauth2() {
        logInButton.click()
    }

}
