plugins {
  id("org.jetbrains.kotlin.jvm")
  id("org.jetbrains.kotlin.plugin.serialization")
}

dependencies {
  shadow(project(":p-api"))
  shadow(project(":p-core"))
  shadow(project(":p-inputs:common"))
  shadow(libraries.kotlinx.serialization.json)
}