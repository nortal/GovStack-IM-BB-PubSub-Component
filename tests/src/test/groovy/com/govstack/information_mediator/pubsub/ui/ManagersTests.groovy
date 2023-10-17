package com.govstack.information_mediator.pubsub.ui

import com.codeborne.selenide.SelenideElement
import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.commons.util.UUIDGenerator
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import com.govstack.information_mediator.pubsub.steps.KeyCloakPage
import com.govstack.information_mediator.pubsub.steps.management_ui.LoginPage
import com.govstack.information_mediator.pubsub.steps.management_ui.PubSubManagersPage
import com.govstack.information_mediator.pubsub.steps.management_ui.common.HeaderBar
import com.govstack.information_mediator.pubsub.steps.management_ui.common.SnackBar
import groovy.sql.GroovyRowResult
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import spock.lang.Issue
import spock.lang.Tag

import static com.codeborne.selenide.Condition.text
import static com.codeborne.selenide.Condition.visible
import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.INTERNAL
import static com.govstack.information_mediator.pubsub.domain.entity.IdentifierType.XROAD
import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.is
import static org.hamcrest.Matchers.not

@Tag("PubSub")
@Tag("UI")
@Epic("Management-ui")
@Feature("PubSub Managers")
class ManagersTests extends GenericSpecification {

    def login() {
        LoginPage.open()
        LoginPage.logInWithOauth2()
        KeyCloakPage.logIn()
    }

    def cleanup() {
        HeaderBar.logOut()
        LoginPage.title.shouldBe(visible)
    }

    @Story("View managers")
    def "Given: managers exist and admin is logged in; When: admins open managers page; Then: manager added and all active managers are listed"() {
        given: "and managers exist"
        List<GroovyRowResult> managersFromDb = toListOfMaps(db.getAllActiveManagers())
        int managersFromDbSize = managersFromDb.size()
        // Add some manager's if not enough
        if (managersFromDbSize < 25) {
            db.addManagers(25 - managersFromDbSize)
            managersFromDb = toListOfMaps(db.getAllActiveManagers())
            managersFromDbSize = managersFromDb.size()
        }

        and: "admin is logged in"
        login()

        when: "admin opens managers page"
        HeaderBar.openManagersPage()

        then: "all active managers are listed"
        ArrayList<Map> displayedManagers = PubSubManagersPage.getManagersAsListOfMaps()
        assertThat(displayedManagers.size(), is(10))
        assertThat(PubSubManagersPage.pagingInfo.text(), is("1-10 of $managersFromDbSize".toString()))
        assertThat(displayedManagers, is(managersFromDb.subList(0, 10)))

        and: "listing functionality works "
        PubSubManagersPage.selectItemPerPage(2)
        ArrayList<Map> displayedManagers2 = PubSubManagersPage.getManagersAsListOfMaps()
        assertThat(displayedManagers2.size(), is(2))
        assertThat(PubSubManagersPage.pagingInfo.text(), is("1-2 of $managersFromDbSize".toString()))
        assertThat(displayedManagers2, is(managersFromDb.subList(0, 2)))

        PubSubManagersPage.selectItemPerPage(25)
        ArrayList<Map> displayedManagers3 = PubSubManagersPage.getManagersAsListOfMaps()
        assertThat(displayedManagers3.size(), is(25))
        assertThat(PubSubManagersPage.pagingInfo.text(), is("1-25 of $managersFromDbSize".toString()))
        assertThat(displayedManagers3, is(managersFromDb.subList(0, 25)))
        //TODO: Add navigation between pages
        //TODO: Add ordering
    }

    @UseCase("UC1.1")
    @Story("UC1.1 - Create manager")
    def "Given: admin is logged in; When: admin creates #managerType manager; Then: manager added"() {
        given: "admin is logged in"
        login()

        when: "admin creates manager"
        if (managerType == XROAD) {
            db.terminateActiveManagerByIdentifier(managerIdentifier)
        }
        HeaderBar.openManagersPage()
        PubSubManagersPage.createManager(managerType, managerIdentifier)

        then: "manager added"
        SnackBar.content.shouldHave(text("Manager $managerIdentifier was successfully created"))
        assertThat(db.getActiveManagers(managerIdentifier, managerType).size(), is(1))
        //TODO: Add check that the manager list is refreshed

        where:
        managerType | managerIdentifier
        INTERNAL    | "${UUIDGenerator.randomUUID()}-internal-manager"
        XROAD       | "SANDBOX:ORG:CLIENT:TEST"
    }

    @Issue("M604017-122")
    @UseCase("UC1.2")
    @Story("UC1.2 - Terminate manager")
    def "Given: a manager exists and admin logs in; When: admin terminates manager; Then: manager terminated"() {
        given: "and managers exist"
        if (db.getAllActiveManagers().size() == 0) {
            db.addManagers(1)
        }
        and: "admin is logged in"
        login()

        when: "admin terminates manager"
        HeaderBar.openManagersPage()
        final def (SelenideElement managerRow, String managerIdentifier) = PubSubManagersPage.getManager(0)
        PubSubManagersPage.terminateManager(managerRow)
        //PubSubManagersPage.TerminateManagerDialog.text.shouldBe(text("Are you sure want to terminate manager $managerIdentifier ?"))
        PubSubManagersPage.TerminateManagerDialog.clickYes()

        then: "manager terminated"
        //Snackbar.content.shouldHave(text("Manager $managerIdentifier was successfully created"))
        assertThat(db.getManagers(managerIdentifier)[0].deleted_at, is(not(null)))
        //TODO: Add check that the manager list is refreshed
    }

    ArrayList toListOfMaps(List<GroovyRowResult> managers) {
        ArrayList list = []
        for (manager in managers) {
            list.add([name: manager.identifier, type: manager.identifier_type])
        }
        return list
    }

}
