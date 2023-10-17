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
@Feature("POST /rooms/{memberId}/{roomId}/publishers")
class AddPublisherTests extends GenericSpecification {

    @UseCase("UC3.6")
    @Story("UC3.6 - Add publisher")
    def "Given: room with multiple event types; When: manager adds publisher for #eventTypeCount event type(s); Then: publisher added for #eventTypeCount event type(s)"() {
        given: "room with multiple event types"
        when: "manager adds publisher for event type(s)"
        then: "publisher added for event type(s)"

        where:
        eventTypeCount | _
        "one"          | _
        "all"          | _
    }

}
