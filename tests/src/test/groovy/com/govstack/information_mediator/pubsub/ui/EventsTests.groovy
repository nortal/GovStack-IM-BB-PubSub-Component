package com.govstack.information_mediator.pubsub.ui

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
class EventsTests extends GenericSpecification {

    @Story("View Events")
    def "Given: a room with events; When: user opens events list; Then: events published to the room from the last 7 days are listed"() {
        given: "a room with events"
        when: "user opens events list"
        then: "events published to the room from the last 7 days are listed"
    }

    @Story("Filter Events List")
    def "Given: a room with events (different: event types, event type versions, publishers); When: user filters events list by '#filterBy'; Then: only events matching '#filterBy' values are listed"() {
        given: "a room with events (different event types, event type versions, publishers)"
        when: "user filters events list"
        then: "only events matching filter listed"

        where:
        filterBy                                | _
        "Time range"                            | _
        "Event type"                            | _
        "Version"                               | _
        "Event publisher"                       | _
        "Event type, Version & Event publisher" | _
    }

    @Story("View event details")
    def "Given: a room with events (where #event); When: user opens event details; Then: #displayedInfo are displayed"() {
        given: "a room with events (different event types, event type versions, publishers)"
        when: "user filters events list"
        then: "only events matching filter listed"

        where:
        event                                                 | displayedInfo
        "active event type, event type version and publisher" | "Event (identifier, correlation id, created at), Event Type (identifier, version number, version JSON schema), Publisher (identifier, identifier type)"
        "deleted event type and publisher"                    | "Event (identifier, correlation id, created at), Event Type (identifier, deleted at, version number, version JSON schema, version deleted at), Publisher (identifier, identifier type, deleted at)"

    }

}
