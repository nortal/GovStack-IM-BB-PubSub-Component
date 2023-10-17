package com.govstack.information_mediator.pubsub.shared.jooq;

import com.govstack.information_mediator.pubsub.commons.JsonService;
import com.govstack.information_mediator.pubsub.commons.exception.InternalErrorException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.JSONB;

@Slf4j
@RequiredArgsConstructor
class RoomsRecordService {

    private final RoomsConfigurationSchemaValidator configurationValidator = new RoomsConfigurationSchemaValidator();

    private final JsonService jsonService;

    void validateConfiguration(JSONB configuration) {
        if (!configurationValidator.isValid(jsonService.readTree(configuration.data()))) {
            throw new InternalErrorException("Invalid room configuration");
        }
    }
}
