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
@Feature("POST /rooms/{memberId}/{roomId}/event-types")
class AddEventTypeTests extends GenericSpecification {

    @UseCase("UC3.3")
    @Story("UC3.3 - Add Event Type")
    def "Given: a room; When: manager adds event type with valid JSON schema to room; Then: event type with version 1 saved in room"() {
        given: "a room"
        when: "manager adds event type with valid JSON schema to room"
        then: "event type with version 1 saved in room"
    }
}
