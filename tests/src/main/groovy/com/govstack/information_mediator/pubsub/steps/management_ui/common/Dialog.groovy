package com.govstack.information_mediator.pubsub.steps.management_ui.common

import com.codeborne.selenide.SelenideElement

import static com.codeborne.selenide.Selenide.$

class Dialog {

    static SelenideElement dialog = $("[data-test='dialog-simple']")
    static SelenideElement saveButton = $("[data-test='dialog-save-button']")

}
