package com.govstack.information_mediator.pubsub.steps.management_ui

import com.codeborne.selenide.ElementsCollection
import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.domain.entity.IdentifierType
import com.govstack.information_mediator.pubsub.steps.management_ui.common.Dialog
import com.govstack.information_mediator.pubsub.steps.management_ui.common.Dropdown
import io.qameta.allure.Step

import java.time.Duration

import static com.codeborne.selenide.Condition.visible
import static com.codeborne.selenide.Selenide.$
import static com.codeborne.selenide.Selenide.$$

class PubSubManagersPage {

    static SelenideElement createManagerButton = $(".icon-Add")
    static SelenideElement pagingInfo = $(".v-data-table-footer__info")

    static class CreateManagerPopUp extends Dialog {

        static SelenideElement managersTypeDropdown = dialog.$x("*//div[contains(@role,'button')]")
        static SelenideElement managersIdentifierField = dialog.$x("*//label[text()='Managers identifier']/following-sibling::input")

        static selectFromManagerDropdown(String textToSelect) {
            managersTypeDropdown.click()
            Dropdown.select(textToSelect)
        }

        static selectFromManagerIdentifierDropdown(String textToSelect) {
            dialog.$$x("*//div[contains(@role,'button')]")[1].click()
            Dropdown.select(textToSelect)
        }

        static clickAdd() {
            saveButton.click()
        }

    }

    static class TerminateManagerDialog extends Dialog {
        static SelenideElement text = dialog.$(".v-card-text")

        static void clickYes() {
            saveButton.click()
            saveButton.$(".v-btn__loader").shouldNotBe(visible)
        }
    }

    @Step("Create manager")
    static void createManager(IdentifierType identifierType, String managersIdentifier) {
        createManagerButton.click()
        CreateManagerPopUp.dialog.shouldBe(visible)
        CreateManagerPopUp.selectFromManagerDropdown(identifierType.toString())
        if (identifierType == IdentifierType.INTERNAL) {
            CreateManagerPopUp.managersIdentifierField.setValue(managersIdentifier)
        } else {
            CreateManagerPopUp.selectFromManagerIdentifierDropdown(managersIdentifier)
        }
        CreateManagerPopUp.clickAdd()
    }

    static ElementsCollection getManagers() {
        return $$(".v-data-table__tr")
    }

    static def getManager(int indexInList) {
        SelenideElement manager = getManagers()[indexInList]
        return [manager, manager.$$(".v-data-table__td")[0].text()]
    }

    static void terminateManager(SelenideElement managerRow) {
        managerRow.$(".v-btn__content").click()
    }

    static ArrayList<Map> getManagersAsListOfMaps() {
        ArrayList<Map> list = []
        for (manager in $$(".v-data-table__tr")) {
            Map managerMap = [name: manager.$$(".v-data-table__td")[0].text(),
                              type: manager.$$(".v-data-table__td")[1].text()]
            list.add(managerMap)
        }
        return list
    }

    @Step("Select items per page")
    static void selectItemPerPage(int cnt) {
        $(".v-select__selection").click()
        Dropdown.select(cnt.toString())
        waitForLoading()
    }

    static void waitForLoading(int seconds = 30) {
        $(".v-data-table-progress").shouldNotBe(visible, Duration.ofSeconds(seconds))
    }
}
