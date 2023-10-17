package com.govstack.information_mediator.pubsub.api.management.publishers

import com.govstack.information_mediator.pubsub.config.GenericSpecification
import io.qameta.allure.Epic
import io.qameta.allure.Feature
import io.qameta.allure.Link
import spock.lang.Tag

@Link("TESTED MANUALLY")
@Tag("PubSub")
@Tag("API")
@Epic("Management-api")
@Feature("PUT /rooms/{memberId}/{roomId}/publishers/{publisherId}")
class EditPublisherTests extends GenericSpecification {
}
