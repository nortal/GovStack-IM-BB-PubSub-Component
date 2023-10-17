plugins {
    id("java")
    id("org.kordamp.gradle.jandex")
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

    implementation(project(":messaging-api:domain"))

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

    implementation("com.networknt:json-schema-validator:_")

    implementation("io.quarkus:quarkus-resteasy-reactive:_")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson:_")
    implementation("io.quarkus:quarkus-rest-client-reactive-jackson:_")
    implementation("io.quarkus:quarkus-smallrye-health:_")
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus:_")
    implementation("io.quarkus:quarkus-smallrye-openapi:_")
    implementation("io.quarkus:quarkus-hibernate-validator:_")

    testImplementation(Square.okHttp3)
    testImplementation(Square.okHttp3.mockWebServer)

    testImplementation("org.hamcrest:java-hamcrest:_")
    testImplementation(Testing.assertj.core)
    testImplementation(Testing.mockito.core)
    testImplementation(Testing.mockito.junitJupiter)
    testImplementation(Testing.junit.jupiter.engine)
    testImplementation(Testing.junit.jupiter.params)
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

tasks.named("compileTestJava").configure {
    dependsOn("jandex")
}
