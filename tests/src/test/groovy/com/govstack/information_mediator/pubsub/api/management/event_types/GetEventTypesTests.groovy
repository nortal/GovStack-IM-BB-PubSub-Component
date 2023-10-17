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
@Feature("GET /rooms/{memberId}/{roomId}/event-types")
class GetEventTypesTests extends GenericSpecification {

    @UseCase("UC3.5")
    @Story("UC3.5 - List event types in room")
    def "Given: a room with event types (active & terminated); When: manager lists event types; Then: rooms' active event types are returned"() {
        given: "a room with event types (active & terminated)"
        when: "manager gets event types"
        then: "rooms' active event types are returned"
    }
}
