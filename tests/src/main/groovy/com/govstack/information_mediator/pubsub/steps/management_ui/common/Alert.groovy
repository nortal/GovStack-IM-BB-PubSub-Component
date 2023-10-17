package com.govstack.information_mediator.pubsub.steps.management_ui.common

import com.codeborne.selenide.SelenideElement

import static com.codeborne.selenide.Selenide.$

class Alert {

    static SelenideElement alertBox = $("[data-test='contextual-alert']")

}
