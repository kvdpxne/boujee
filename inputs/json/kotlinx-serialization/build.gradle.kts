plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  shadow(project(":api"))
  shadow(project(":core"))
  shadow(project(":inputs-common"))
  shadow(libraries.kotlinx.serialization.json)
}