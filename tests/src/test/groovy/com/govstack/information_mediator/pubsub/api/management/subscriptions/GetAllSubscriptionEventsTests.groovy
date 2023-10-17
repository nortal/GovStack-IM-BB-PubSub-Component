package com.govstack.information_mediator.pubsub.api.management.subscriptions

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Story
import spock.lang.Tag

@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("GET /rooms/{memberId}/{roomId}/subscriptions/{subscriptionId}/queue")
class GetAllSubscriptionEventsTests extends GenericSpecification {

    @UseCase("UC4.5")
    @Story("UC4.5 - List all subscriptions with details")
    def "Given: a room with multiple subscriptions exist; When: manager lists all subscriptions; Then: all subscriptions with details returned"() {
        given: "a room with multiple subscriptions exist"
        when: "manager lists all subscriptions"
        then: "all subscriptions with details returned"
    }

}
