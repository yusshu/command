plugins {
    `java-library`
    `maven-publish`
}

dependencies {
    implementation("org.jetbrains:annotations:21.0.1")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
