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
class EventTypeTests extends GenericSpecification {

    @UseCase("UC3.5")
    @Story("UC3.5 - View Event Types list")
    def "Given: a room with event types (active & terminated); When: user opens Event Types list; Then: rooms' active event types are listed"() {
        given: "a room with event types (active & terminated)"
        when: "user opens Event Types list"
        then: "rooms' active event types are listed"
    }

    @UseCase("UC3.3")
    @Story("UC3.3 - Add Event Type")
    def "Given: a room; When: user adds event type with valid JSON schema to room; Then: event type with version 1 saved in room"() {
        given: "a room"
        when: "user adds event type with valid JSON schema to room"
        then: "event type with version 1 saved in room"
    }

    @UseCase("UC3.4")
    @Story("UC3.4 - Add Event Type version")
    def "Given: a room with event type with version 1; When: user adds new version with valid JSON schema to event type; Then: event type version 2 saved"() {
        given: "a room with event type with version 1"
        when: "user adds new version with valid JSON schema to event type"
        then: "event type version 2 saved"
    }

    @Story("View Event Type versions list")
    def "Given: a room with event type that has multiple event type versions; When: user views event type versions list; Then: event type non-terminated versions listed"() {
        given: "a room with event type that has multiple event type versions"
        when: "user views event type versions list"
        then: "event type non-terminated versions listed"
    }

    @Story("Download Event Type versions schema")
    def "Given: a room with event type; When: user downloads event type version; Then: event type schema JSON is downloaded"() {
        given: "a room with event type"
        when: "user downloads event type version"
        then: "event type schema JSON is downloaded"
    }

    @Story("Edit Event Type version")
    def "Given: a room with event type; When: user edits event type version; Then: event type version successfully updated"() {
        given: "a room with event type"
        when: "user edits event type version"
        then: "event type version successfully updated"
    }

    @UseCase("UC3.9")
    @Story("UC3.9 - Terminate Event Type")
    def "Given: a room with event type; When: user terminates event type; Then: event type is terminated and related entities (event type versions, publishers, subscriptions) terminated"() {
        given: "a room with event type"
        when: "user terminates event type"
        then: "event type is terminated and related entities (event type versions, publishers, subscriptions) terminated"
    }

    @UseCase("UC3.9")
    @Story("UC3.9 - Terminate Event Type version")
    def "Given: a room with event type that has multiple versions; When: user terminates an event type version; Then: event type version is terminated"() {
        given: "a room with event type that has multiple versions"
        when: "user terminates an event type version"
        then: "event type version is terminated"
    }

}
