plugins {
  id("org.jetbrains.kotlin.jvm")
}

dependencies {
  shadow(project(":api"))
  shadow(project(":core"))
}