plugins {
    id("java")
}

group = "com.govstack.information_mediator.pubsub"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared:commons"))

    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
    implementation ("org.projectlombok:lombok:_")

    implementation("com.networknt:json-schema-validator:_")
}
