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
@Feature("Subscriptions")
class SubscriptionTests extends GenericSpecification {

    @UseCase("UC5.1")
    @Story("UC5.1 - View subscriptions")
    def "Given: #desc is logged in and room with subscriptions in all statuses (PENDING, ACTIVE, REJECTED, TERMINATED) exists; When: #desc opens subscriptions tab in room details; Then: all rooms' subscriptions are listed"() {
        given: "admin is logged in"
        and: "room with subscriptions in all statuses (PENDING, ACTIVE, REJECTED, TERMINATED) exists"
        when: "user opens subscriptions tab in room details"
        then: "all rooms' subscriptions are listed"

        where:
        desc      | user                   | password
        "admin"   | "system-administrator" | "system-administrator"
        "manager" | "internal-manager"     | "pass"
    }

    @UseCase("UC4.2")
    @Story("UC4.2 - Filter subscriptions")
    def "Given: a room with subscriptions in all statuses (PENDING, ACTIVE, REJECTED, TERMINATED) exists; When: user filters rooms' subscriptions by status #status; Then: rooms' subscriptions with status #status are listed"() {
        given: "a room with subscriptions in all statuses (PENDING, ACTIVE, REJECTED, TERMINATED) exists"
        when: "user filters rooms' subscriptions by status"
        then: "rooms' subscriptions with corresponding status listed"

        where:
        status       | _
        "PENDING"    | _
        "ACTIVE"     | _
        "REJECTED"   | _
        "TERMINATED" | _
    }

    @UseCase("UC4.4")
    @Story("UC4.4 - Approve subscription")
    def "Given: a room with subscription in status PENDING; When: user approves subscription; Then: approving succeeds and subscription status set to ACTIVE"() {
        given: "a room with subscription in status PENDING"
        when: "user approves subscription"
        then: "approving succeeds"
        and: "subscription status set to ACTIVE"
    }

    @UseCase("UC4.3")
    @Story("UC4.3 - Reject subscription")
    def "Given: a room with subscription in status PENDING; When: user rejects subscription; Then: rejecting succeeds and subscription status set to REJECTED"() {
        given: "a room with subscription in status PENDING"
        when: "user rejects subscription"
        then: "rejecting succeeds"
        and: "subscription status set to REJECTED"
    }

    @UseCase("UC5.2")
    @Story("UC5.2 - Terminate subscription")
    def "Given: #desc is logged in and room with ACTIVE subscription exists; When: #desc terminates subscription; Then: subscription is terminated"() {
        given: "admin is logged in"
        and: "room with ACTIVE subscription exists"
        when: "user terminates subscription"
        then: "subscription is terminated"

        where:
        desc      | user                   | password
        "admin"   | "system-administrator" | "system-administrator"
        "manager" | "internal-manager"     | "pass"
    }

    @UseCase("UC4.5")
    @Story("UC4.5 - View subscription details")
    def "Given: a room with subscription method #method exist; When: user opens subscription details; Then: subscription details displayed (#displayedDetails)"() {
        given: "a room with subscription exist"
        when: "user opens subscription details"
        then: "subscription details displayed"

        where:
        method | displayedDetails
        "PULL" | "ID, Identifier, Status, Method, Created At"
        "PUSH" | "ID, Identifier, Status, Method, Push URL, Created At"
    }

    @UseCase("UC4.6")
    @Story("UC4.6 - View subscriptions' published events")
    def "Given: a room with #given; When: user opens subscription details; Then: event shown as #shownStatus under 'Published events'"() {
        given: "a room with subscription exists"
        when: "user opens subscription details"
        then: "event shown with the correct status under 'Published events'"

        where:
        given                                                                                                                    | shownStatus
        "subscription method PULL exists and event published to room, but not pulled or expired"                                 | "PUBLISHED"
        "subscription method PULL exists and event published to room and pulled by subscriber"                                   | "DELIVERED"
        "subscription method PULL exists and event published to room and it has expired before it was pulled"                    | "UNCONSUMED"
        "subscription method PUSH exists and event published to room, but not delivered and delivery attempts remaining"         | "PENDING"
        "subscription method PUSH exists and event published to room and delivered to subscriber"                                | "DELIVERED"
        "subscription method PUSH exists and event published to room and it has expired before it could be delivered"            | "UNCONSUMED"
        "subscription method PUSH exists and event published to room, but not delivered and no more delivery attempts remaining" | "UNCONSUMED"


    }

}
