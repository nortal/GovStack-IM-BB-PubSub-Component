plugins {
    id("java")
}

group = "com.govstack.information_mediator.pubsub.shared"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.projectlombok:lombok:_")
    annotationProcessor("org.projectlombok:lombok:_")
}

tasks.test {
    useJUnitPlatform()
}
