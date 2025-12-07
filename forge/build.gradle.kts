plugins {
    id("net.minecraftforge.gradle") version "6.0.+"
}

val minecraftVersion: String by project
val forgeVersion: String by project

base {
    archivesName.set("DisableVillagerTrade-Forge")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

minecraft {
    mappings("official", minecraftVersion)
    
    runs {
        create("client") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            mods {
                create("disablevillagertrade") {
                    source(sourceSets.main.get())
                }
            }
        }
        
        create("server") {
            workingDirectory(project.file("run"))
            property("forge.logging.markers", "REGISTRIES")
            property("forge.logging.console.level", "debug")
            mods {
                create("disablevillagertrade") {
                    source(sourceSets.main.get())
                }
            }
        }
    }
}

repositories {
    maven("https://maven.minecraftforge.net/")
}

dependencies {
    minecraft("net.minecraftforge:forge:$forgeVersion")
    
    // Include common module
    implementation(project(":common"))
    jarJar(project(":common"))
}

tasks {
    processResources {
        val props = mapOf(
            "version" to project.version,
            "minecraft_version" to minecraftVersion,
            "forge_version" to forgeVersion.split("-")[1]
        )
        inputs.properties(props)
        filesMatching("META-INF/mods.toml") {
            expand(props)
        }
    }
    
    jar {
        manifest {
            attributes(
                "Specification-Title" to "DisableVillagerTrade",
                "Specification-Vendor" to "dodo",
                "Specification-Version" to "1",
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Implementation-Vendor" to "dodo"
            )
        }
        finalizedBy("reobfJar")
    }
}
