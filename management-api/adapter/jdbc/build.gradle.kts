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
    implementation(project(":shared:commons"))
    implementation(project(":shared:domain-entity"))
    implementation(project(":shared:jooq"))
    implementation(project(":management-api:domain"))

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

    implementation("org.springframework.boot:spring-boot-starter:_")
    implementation(Spring.boot.jooq)
    implementation("com.fasterxml.jackson.core:jackson-databind:_")
    implementation("org.apache.commons:commons-lang3:_")
    implementation("com.networknt:json-schema-validator:_")
}

sourceSets {
    main {
        java.srcDir("src/main/java")
        resources.srcDir("src/main/resources")
    }
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
