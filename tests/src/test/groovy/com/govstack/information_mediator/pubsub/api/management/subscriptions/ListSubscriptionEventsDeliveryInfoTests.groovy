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
@Feature("GET /rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}/queue")
class ListSubscriptionEventsDeliveryInfoTests extends GenericSpecification {

    @UseCase("UC4.6")
    @Story("UC4.6 - View subscriptions' events")
    def "Given: a room with #given; When: manager lists subscription events with status filter='#status'; Then: only subscriber events with status '#status' returned"() {
        given: "a room with subscription exists"
        when: "manager lists subscription events with status filter"
        then: "only subscriber events with status '#status' returned"

        where:
        given                                                                                                                    | status
        "subscription method PULL exists and event published to room, but not pulled or expired"                                 | "PUBLISHED"
        "subscription method PULL exists and event published to room and pulled by subscriber"                                   | "DELIVERED"
        "subscription method PULL exists and event published to room and it has expired before it was pulled"                    | "UNCONSUMED"
        "subscription method PUSH exists and event published to room, but not delivered and delivery attempts remaining"         | "PENDING"
        "subscription method PUSH exists and event published to room and delivered to subscriber"                                | "DELIVERED"
        "subscription method PUSH exists and event published to room and it has expired before it could be delivered"            | "UNCONSUMED"
        "subscription method PUSH exists and event published to room, but not delivered and no more delivery attempts remaining" | "UNCONSUMED"


    }

}
