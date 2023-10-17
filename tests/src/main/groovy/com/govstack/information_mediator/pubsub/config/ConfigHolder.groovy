package com.govstack.information_mediator.pubsub.config

import com.govstack.information_mediator.pubsub.Utils
import org.aeonbits.owner.ConfigFactory

class ConfigHolder {
    private static TestConfig conf = readConf()

    static TestConfig getConf() {
        return conf
    }

    static TestConfig readConf() {
        // Read local properties override
        URL localOverride = ConfigHolder.class.getResource("/application-local.properties")
        if (!Utils.isRunningInDocker() && localOverride) {
            Properties localProps = new Properties()
            localOverride.withInputStream {
                localProps.load(it)
            }
            return ConfigFactory.create(TestConfig, localProps)
        } else {
            return ConfigFactory.create(TestConfig)
        }
    }
}
