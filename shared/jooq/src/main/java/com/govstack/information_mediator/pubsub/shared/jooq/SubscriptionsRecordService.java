package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;

@Slf4j
@RequiredArgsConstructor
class SubscriptionsRecordService {

    private final SubscriptionsParametersSchemaValidator parametersValidator = new SubscriptionsParametersSchemaValidator();

    private final JsonService jsonService;

    void validateParameters(JSONB parameters) {
        if (!parametersValidator.isValid(jsonService.readTree(parameters.data()))) {
            throw new InternalErrorException("Invalid subscription parameters");
        }
    }
}
