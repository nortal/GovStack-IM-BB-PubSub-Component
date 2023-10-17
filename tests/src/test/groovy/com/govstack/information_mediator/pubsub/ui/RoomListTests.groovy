package com.govstack.information_mediator.pubsub.ui

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Link
import io.qameta.allure.Story
import spock.lang.Tag

@Link("TESTED MANUALLY")
@Tag("PubSub")
@Tag("UI")
@Epic("Management-ui")
@Feature("PubSub Managers")
class RoomListTests extends GenericSpecification {

    @UseCase("UC3.2")
    @Story("UC3.2 - View Rooms List")
    def "Given: rooms exist and #desc is logged in; When: #desc opens 'Rooms List' page; Then: #listedRooms are listed"() {
        given: "rooms exist and admin is logged in"
        when: "admin opens 'Rooms List' page"
        then: "all active rooms are listed"

        where:
        desc      | user                   | password               | listedRooms
        "admin"   | "system-administrator" | "system-administrator" | "all active rooms"
        "manager" | "internal-manager"     | "pass"                 | "managers' rooms"
    }

    @Story("View room details")
    def "Given: rooms exist and #desc is logged in; When: #desc views room details; Then: room details are displayed"() {
        given: "rooms exist and admin is logged in"
        when: "user views room details"
        then: "room details are displayed"

        where:
        desc      | user                   | password
        "admin"   | "system-administrator" | "system-administrator"
        "manager" | "internal-manager"     | "pass"
    }

    @Story("Filter by manager")
    def "Given: rooms exist and admin is logged in; When: admin selects a manager name to be filtered by; Then: selected managers' active rooms are listed"() {
        given: "rooms exist and admin is logged in"
        when: "admin selects a manager name to be filtered by"
        then: "selected managers' active rooms are listed"
    }

    @UseCase("UC3.1")
    @Story("UC3.1 - Create room")
    def "Given: admin is logged in and managers exist; When: admin creates room; Then: room created with the entered configuration"() {
        given: "admin is logged in and managers exist"
        when: "admin creates room"
        then: "room created with the entered configuration"
    }

    @Story("Edit room")
    def "Given: #desc is logged in and room exists; When: #desc edits rooms (#editableConf); Then: room saved with the changed configuration"() {
        given: "admin is logged in and room exists"
        when: "admin edits room"
        then: "room saved with the changed configuration"

        where:
        desc      | editableConf                    | user                   | password
        "admin"   | "room identifier, " +
                    "manager identifier, " +
                    "message expiration, " +
                    "delivery delay, " +
                    "delivery delay multiplier, " +
                    "delivery attempts"             | "system-administrator" | "system-administrator"
        "manager" | "message expiration, " +
                    "delivery delay, " +
                    "delivery delay multiplier, " +
                    "delivery attempts"             | "internal-manager"     | "pass"
    }

    @UseCase("UC3.10")
    @Story("UC3.10 - Terminate room")
    def "Given: #desc is logged in and room exists; When: #desc terminates room; Then: room terminated and related entities (event types, event type versions, publishers, subscriptions) terminated"() {
        given: "admin is logged in and room exists"
        when: "admin terminates room"
        then: "room terminated"
        and: "related entities (event types, event type versions, publishers, subscriptions) terminated"

        where:
        desc      | user                   | password
        "admin"   | "system-administrator" | "system-administrator"
        "manager" | "internal-manager"     | "pass"
    }

}
