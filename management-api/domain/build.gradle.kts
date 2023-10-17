plugins {
    id("java")
    id("jacoco")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))

    compileOnly ("org.projectlombok:lombok:_")
    annotationProcessor ("org.projectlombok:lombok:_")

    implementation("org.springframework:spring-context:_")
    implementation("org.springframework:spring-tx:_")

    implementation("org.apache.commons:commons-lang3:_")
    implementation("com.networknt:json-schema-validator:_")

    testImplementation("org.json:json:_")

    testRuntimeOnly(Testing.junit.jupiter.engine)
    testImplementation(Testing.junit.jupiter.api)
    testImplementation(Testing.junit.jupiter.params)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.mockito.junitJupiter)
    testImplementation(Testing.assertj.core)
    testImplementation("org.hamcrest:java-hamcrest:_")
}

tasks.test {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        xml.outputLocation.set(file("build/reports/jacoco/test/jacocoTestReport.xml"))
    }
}
