plugins {
  id("org.jetbrains.kotlin.jvm")
}

dependencies {
  shadow(project(":p-api"))
  shadow(project(":p-core"))
}