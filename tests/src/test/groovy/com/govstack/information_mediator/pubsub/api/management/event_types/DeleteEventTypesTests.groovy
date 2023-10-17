package com.govstack.information_mediator.pubsub.api.management.event_types

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
@Feature("DELETE /rooms/{memberId}/{roomId}/event-types/{eventType}")
class DeleteEventTypesTests extends GenericSpecification {

    @UseCase("UC3.9")
    @Story("UC3.9 - Terminate Event Type")
    def "Given: a room with event type; When: manager terminates event type; Then: event type is terminated and related entities (event type versions, publishers, subscriptions) terminated"() {
        given: "a room with event type"
        when: "manager terminates event type"
        then: "event type is terminated and related entities (event type versions, publishers, subscriptions) terminated"
    }

}
