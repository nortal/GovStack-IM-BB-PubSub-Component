package com.govstack.information_mediator.pubsub.ui

import com.govstack.information_mediator.pubsub.UseCase
import com.govstack.information_mediator.pubsub.config.GenericSpecification
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Link
import io.qameta.allure.Story
import spock.lang.Tag

@Link("TESTED MANUALLY")
@Tag("PubSub")
@Tag("UI")
@Epic("Management-ui")
@Feature("PubSub Managers")
class PublisherTests extends GenericSpecification {

    @UseCase("UC3.7")
    @Story("UC3.7 - View publishers")
    def "Given: room with publishers; When: user opens publishers tab; Then: rooms' all active publishers are listed"() {
        given: "room with publishers"
        when: "user opens publishers tab"
        then: "rooms' all active publishers are listed"
    }

    @UseCase("UC3.6")
    @Story("UC3.6 - Add publisher")
    def "Given: room with multiple event types; When: user adds publisher for #eventTypeCount event type(s); Then: publisher added for #eventTypeCount event type(s)"() {
        given: "room with multiple event types"
        when: "user adds publisher for event type(s)"
        then: "publisher added for event type(s)"

        where:
        eventTypeCount | _
        "one"          | _
        "all"          | _
    }

    @Story("View publishers' event types")
    def "Given: room with publishers; When: user opens publishers' event types; Then: publishers' event types are listed"() {
        given: "room with publishers"
        when: "user opens publishers' event types"
        then: "publishers' event types are listed"
    }

    @Story("Update publisher")
    def "Given: room with multiple event types and publisher to 1 event type; When: user updates publisher to 2 event types; Then: publisher can publish events to 2 event types"() {
        given: "room with multiple event types and publisher to 1 event type"
        when: "user updates publisher to 2 event types"
        then: "publisher can publish events to 2 event types"
    }

    @UseCase("UC3.8")
    @Story("UC3.8 - Terminate publisher")
    def "Given: room with publishers; When: user terminates publisher; Then: publisher is terminated"() {
        given: "room with publishers"
        when: "user terminates publisher"
        then: "publisher is terminated"
    }

}
