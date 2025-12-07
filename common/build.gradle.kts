plugins {
    `java-library`
}

base {
    archivesName.set("disablevillagertrade-common")
}

dependencies {
    // Common module has no platform-specific dependencies
    // Only pure Java code for shared logic
    
    // Test dependencies
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.3")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.11.3")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

