package com.govstack.information_mediator.pubsub.steps.management_ui.common

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement

import static com.codeborne.selenide.CollectionCondition.containExactTextsCaseSensitive
import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$

class Dropdown {

    static SelenideElement dropdownItems = $(".v-list")

    static select(String textToSelect) {
        $(".v-list").shouldBe(visible)
        Thread.sleep(1000)
        $(".v-list").$$(".v-list-item-title").should(containExactTextsCaseSensitive(textToSelect))
        ElementsCollection itemList = $(".v-list").$$(".v-list-item-title")
        SelenideElement itemToSelect = itemList.filterBy(text(textToSelect))[0]
        itemToSelect.shouldBe(visible)
        itemToSelect.click()
    }

}
