package com.govstack.information_mediator.pubsub.api.management.publishers

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
@Feature("GET /rooms/{memberId}/{roomId}/publishers")
class GetPublishersTests extends GenericSpecification {

    @UseCase("UC3.7")
    @Story("UC3.7 - List all publishers in a room")
    def "Given: room with publishers; When: user lists publishers in a room; Then: rooms' all active publishers are returned"() {
        given: "room with publishers"
        when: "user lists publishers in a room"
        then: "rooms' all active publishers are returned"
    }

}
