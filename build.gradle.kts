plugins {
  libraries.plugins.run {
    alias(kotlin)
    alias(shadow)
  }
}

allprojects {
  group = "me.kvdpxne"
  version = "0.1.0"
}

subprojects {

  apply {
    plugin("java")
    plugin("org.jetbrains.kotlin.jvm")
    plugin("com.gradleup.shadow")
  }

  tasks {

    withType<Test> {
      useJUnitPlatform()
    }
  }
}

tasks {

  wrapper {
    distributionType = Wrapper.DistributionType.ALL
  }
}