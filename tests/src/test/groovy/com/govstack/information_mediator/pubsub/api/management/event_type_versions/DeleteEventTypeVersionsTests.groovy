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
@Feature("DELETE /rooms/{memberId}/{roomId}/event-types/{event-type}/versions")
class DeleteEventTypeVersionsTests extends GenericSpecification {

    @UseCase("UC3.9")
    @Story("UC3.9 - Terminate Event Type version")
    def "Given: a room with event type that has multiple versions; When: manager terminates an event type version; Then: event type version is terminated"() {
        given: "a room with event type that has multiple versions"
        when: "manager terminates an event type version"
        then: "event type version is terminated"
    }

}
