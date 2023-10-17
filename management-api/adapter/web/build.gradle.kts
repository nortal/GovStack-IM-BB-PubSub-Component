plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("jacoco")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":management-api:domain"))

    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))

    implementation("org.springframework.boot:spring-boot-starter:_")
    implementation(Spring.boot.web)
    implementation("org.springframework.boot:spring-boot-starter-json:_")
    implementation(Spring.boot.validation)
    implementation(Spring.boot.actuator)
    implementation(Spring.boot.security)
    implementation(Spring.boot.oauth2ResourceServer)

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:_")

    implementation("io.micrometer:micrometer-registry-prometheus:_")

    implementation("com.networknt:json-schema-validator:_")
    implementation("jakarta.validation:jakarta.validation-api:_")
    implementation("org.glassfish:jakarta.el:_")
    implementation("org.hibernate.validator:hibernate-validator:_")

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

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

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
