plugins {
    java
    id("com.github.johnrengelman.shadow") version ("7.0.0")
}

group = "at.helpch.placeholderapi.expansion"
version = "2.7.3"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://repo.helpch.at/releases")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20.6-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.5")
}

tasks {
    jar {
        manifest {
            attributes["Implementation-Title"] = "server"
            attributes["Implementation-Version"] = project.version
        }
    }

    java {
        disableAutoTargetJvm()
        targetCompatibility = JavaVersion.VERSION_1_8
        sourceCompatibility = JavaVersion.VERSION_1_8
    }

    shadowJar {
        archiveFileName.set("PAPI-Expansion-Server-${project.version}.jar")
    }
}