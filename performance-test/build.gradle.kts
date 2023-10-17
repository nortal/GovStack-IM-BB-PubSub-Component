import io.gatling.gradle.GatlingRunTask

plugins {
    kotlin("jvm") version "1.9.10"
    id("io.gatling.gradle")
}

repositories {
    mavenCentral()
}

dependencies {
    gatling("ru.tinkoff:gatling-jdbc-plugin_2.13:_")
    gatlingRuntimeOnly("org.postgresql:postgresql:_")
}
