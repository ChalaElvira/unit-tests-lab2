plugins {
    id("java")
}

group = "org.elvira"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
    reports.html.required.set(true)
    reports.junitXml.required.set(true)
}
