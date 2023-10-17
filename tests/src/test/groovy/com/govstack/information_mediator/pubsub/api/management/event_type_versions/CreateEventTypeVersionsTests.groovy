package com.govstack.information_mediator.pubsub.api.management.event_type_versions

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
@Feature("POST /rooms/{memberId}/{roomId}/event-types/{event-type}/versions")
class CreateEventTypeVersionsTests extends GenericSpecification {

    @UseCase("UC3.4")
    @Story("UC3.4 - Add Event Type version")
    def "Given: a room with event type with version 1; When: manager adds new version with valid JSON schema to event type; Then: event type version 2 saved"() {
        given: "a room with event type with version 1"
        when: "manager adds new version with valid JSON schema to event type"
        then: "event type version 2 saved"
    }

}
