plugins {
    java
    id("com.gradleup.shadow") version "8.3.6" apply false
    jacoco
}

jacoco {
    toolVersion = "0.8.12"
}

repositories {
    mavenCentral()
}

val modVersion: String by project
val mavenGroup: String by project

allprojects {
    group = mavenGroup
    version = modVersion
}

subprojects {
    apply(plugin = "java")
    apply(plugin = "jacoco")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(21))
        }
    }

    repositories {
        mavenCentral()
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    tasks.withType<Jar> {
        from(rootProject.file("LICENSE")) {
            rename { "${it}_${rootProject.name}" }
        }
    }

    tasks.withType<JacocoReport> {
        reports {
            xml.required.set(true)
            html.required.set(true)
        }
    }

    tasks.withType<Test> {
        finalizedBy(tasks.withType<JacocoReport>())
    }
}

// Aggregate coverage report for all modules
tasks.register<JacocoReport>("jacocoRootReport") {
    dependsOn(subprojects.flatMap { it.tasks.withType<Test>() })
    
    additionalSourceDirs.setFrom(files(subprojects.flatMap { 
        it.the<SourceSetContainer>()["main"].allSource.srcDirs 
    }))
    sourceDirectories.setFrom(files(subprojects.flatMap { 
        it.the<SourceSetContainer>()["main"].allSource.srcDirs 
    }))
    classDirectories.setFrom(files(subprojects.flatMap { 
        it.the<SourceSetContainer>()["main"].output 
    }))
    executionData.setFrom(files(subprojects.flatMap {
        it.tasks.withType<JacocoReport>().map { task -> task.executionData }
    }).filter { it.exists() })

    reports {
        xml.required.set(true)
        xml.outputLocation.set(layout.buildDirectory.file("reports/jacoco/jacocoRootReport/jacocoRootReport.xml"))
        html.required.set(true)
        html.outputLocation.set(layout.buildDirectory.dir("reports/jacoco/jacocoRootReport/html"))
    }
}
