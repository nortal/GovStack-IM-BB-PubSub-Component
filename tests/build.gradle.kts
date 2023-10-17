group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

plugins {
    groovy
    id("io.qameta.allure")
    id("com.adarshr.test-logger")
}

repositories {
    mavenCentral()
}
dependencies {
    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))
    implementation("org.apache.groovy:groovy-all:_")
    implementation("io.qameta.allure:allure-spock2:_")
    implementation("io.qameta.allure:allure-rest-assured:_")
    implementation("org.spockframework:spock-core:_")
    implementation("io.rest-assured:rest-assured:_")
    implementation("com.codeborne:selenide:_")
    implementation("io.qameta.allure:allure-selenide:_")
    implementation("org.aeonbits.owner:owner:_")
    implementation("org.postgresql:postgresql:_")
}

allure {
    version.set("2.23.0")
    adapter {
        // AspectJ Weaver is fine, but make sure the latest available version is used
        aspectjWeaver.set(true)
        aspectjVersion.set("1.9.19")

        // in order to disable dependencySubstitution (spi-off classifier)
        autoconfigureListeners.set(true)

        // disable auto-configuring dependencies
        autoconfigure.set(false)
        afterEvaluate {
            frameworks.forEach { adapter -> adapter.enabled.set(false) }
        }
    }
}

tasks.test {
    useJUnitPlatform {
        val testFilterInclude = System.getProperty("testFilterInclude")
        if (testFilterInclude != null) {
            includeTags(*testFilterInclude.split(",").toTypedArray())
            project.logger.lifecycle("Include tests with tags: $testFilterInclude")
        }
        val testFilterExclude = System.getProperty("testFilterExclude")
        if (testFilterExclude != null) {
            excludeTags(*testFilterExclude.split(",").toTypedArray())
            project.logger.lifecycle("Exclude tests with tags: $testFilterExclude")
        }
    }
}

testlogger {
    slowThreshold = 30000
}

