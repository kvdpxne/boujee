pluginManagement {

  repositories {
    gradlePluginPortal()

    mavenCentral()
    mavenLocal()
  }
}

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
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
  }

  repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
}

sequenceOf(
  "api",
  "benchmark",
  "chains",
  "core",
  "examples:complex",
  "examples:minecraft:bukkit",
  "examples:simple",
  "inputs:common",
  "inputs:json:gson",
  "inputs:json:kotlinx-serialization",
  "minecraft:api",
  "minecraft:bukkit",
  "singleton:java",
  "singleton:kotlin"
).forEach {
  val name = it.replace(':', '-')
  val directory = file("./${it.replace(':', '/')}")

  include(name)
  project(":$name").projectDir = directory
}

rootProject.name = "boujee"
