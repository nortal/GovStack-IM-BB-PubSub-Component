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
@Feature("DELETE /rooms/{memberId}/{roomId}/publishers/{publisherId}")
class DeletePublisherTests extends GenericSpecification {

    @UseCase("UC3.8")
    @Story("UC3.8 - Terminate publisher")
    def "Given: room with publishers; When: manager terminates publisher; Then: publisher is terminated"() {
        given: "room with publishers"
        when: "manager terminates publisher"
        then: "publisher is terminated"
    }

}
