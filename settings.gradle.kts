pluginManagement {

  plugins {
    id("java")
    id("maven-publish")
  }

  repositories {
    gradlePluginPortal()

    mavenCentral()
    mavenLocal()
  }
}

//plugins {
//  id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
//}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {

  versionCatalogs {
    val fileName = "libraries"
    create(fileName) {
      from(files("gradle/$fileName.versions.toml"))
    }
  }

  repositories {
    mavenCentral()
    mavenLocal()

    maven("https://oss.sonatype.org/content/repositories/snapshots")

    // Maven repositories containing older versions of spigot-api, such as
    // 1.7.x, 1.6.x and 1.5.x, and all newer versions of spigot-api from
    // 1.8 upwards.
    maven("https://repo.md-5.net/content/repositories/public/")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots")
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

rootProject.name = "boujee"

sequenceOf(
  "api",
  "chains",
  "core",
  "inputs/common",
  "inputs/json/gson",
  "inputs/json/kotlinx-serialization",
  "minecraft/api",
  "minecraft/bukkit",
  "singleton/java",
  "singleton/kotlin"
).forEach {
  val rawName = it.replace('/', '-')
  val name = "${rootProject.name}-$rawName"

  include(name)
  project(":$name").projectDir = file("./$it")
}


