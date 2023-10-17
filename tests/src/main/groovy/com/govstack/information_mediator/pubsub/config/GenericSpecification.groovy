package com.govstack.information_mediator.pubsub.config

import com.govstack.information_mediator.pubsub.Database
import spock.lang.Shared
import spock.lang.Specification

abstract class GenericSpecification extends Specification {

    // initialized only ONCE over all threads (important for parallelization)
    static BeforeAll beforeAll = new BeforeAll()

    @Shared
    TestConfig config = ConfigHolder.getConf()

    // Databases
    Database db = new Database(
            url: config.dbUrl(),
            username: config.dbUsername(),
            password: config.dbPassword()
    )

}
