import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

group = "dev.alexzvn.petstats"
version = "1.0.1"

java.sourceCompatibility = JavaVersion.VERSION_16

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://nexus.phoenixdevt.fr/repository/maven-public")
    maven("https://mvn.lumine.io/repository/maven-public/")
}


dependencies {
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")

    compileOnly("io.lumine:MythicLib-dist:1.6.2-SNAPSHOT")
    compileOnly("net.Indyuce:MMOCore-API:1.12.1-SNAPSHOT")
    compileOnly("io.lumine:Mythic-Dist:5.3.5")
    compileOnly("fr.nocsy:mcpets:2.0.0-SNAPSHOT")
    compileOnly("net.luckperms:api:5.4")

    shadow("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
        kotlinOptions.jvmTarget = "1.8"
    }

    shadowJar {
        archiveBaseName.set("FancyPetStats")
        archiveClassifier.set("")
        archiveVersion.set(project.version.toString())

        minimize()

        manifest {
            attributes(mapOf("Main-Class" to "$group/Main"))
        }
    }

    named<ProcessResources>("processResources") {
        expand(project.properties)
    }
}

tasks.build {
    dependsOn("shadowJar")
}
