package com.govstack.information_mediator.pubsub.api.management.subscriptions

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Link
import io.qameta.allure.Story
import spock.lang.Tag

@Link("TESTED MANUALLY")
@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("POST /rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}")
class HandleSubscriptionTests extends GenericSpecification {

    @UseCase("UC4.4")
    @Story("UC4.4 - Approve subscription")
    def "Given: a room with subscription in status PENDING; When: manager approves subscription; Then: approving succeeds and subscription status set to ACTIVE"() {
        given: "a room with subscription in status PENDING"
        when: "manager approves subscription"
        then: "approving succeeds"
        and: "subscription status set to ACTIVE"
    }

    @UseCase("UC4.3")
    @Story("UC4.3 - Reject subscription")
    def "Given: a room with subscription in status PENDING; When: manager rejects subscription; Then: rejecting succeeds and subscription status set to REJECTED"() {
        given: "a room with subscription in status PENDING"
        when: "manager rejects subscription"
        then: "rejecting succeeds"
        and: "subscription status set to REJECTED"
    }

    @UseCase("UC5.2")
    @Story("UC5.2 - Terminate subscription")
    def "Given: a room with #status subscription exists; When: manager terminates subscription; Then: subscription is terminated"() {
        given: "admin is logged in"
        and: "room with subscription exists"
        when: "user terminates subscription"
        then: "subscription is terminated"

        where:
        status    | _
        "ACTIVE"  | _
        "PENDING" | _
    }

}
