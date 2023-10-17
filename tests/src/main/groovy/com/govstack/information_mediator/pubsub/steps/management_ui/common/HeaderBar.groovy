package com.govstack.information_mediator.pubsub.steps.management_ui.common

import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.steps.management_ui.PubSubManagersPage
import io.qameta.allure.Step

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$

class HeaderBar {

    static SelenideElement menu = $(".tabs-wrap")
    static SelenideElement pubsubManagersLink = $("[data-test='managers']")
    static SelenideElement userNameButton = $("[data-test='username-button']")
    static SelenideElement logOutLink = $("[data-test='logout-list-tile']")

    @Step("Log out")
    static void logOut() {
        userNameButton.click()
        logOutLink.shouldBe(visible)
        logOutLink.click()
    }

    @Step("Open 'PubSub Managers' page")
    static void openManagersPage() {
        pubsubManagersLink.click()
        PubSubManagersPage.waitForLoading()
    }

}
