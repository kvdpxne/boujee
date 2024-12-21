plugins {
  libraries.plugins.run {
    alias(champeau.jmh).apply(false)
    alias(kotlin).apply(false)
    alias(kotlin.serialization).apply(false)
    alias(shadow)
  }

  id("java")
  id("maven-publish")
}

allprojects {
  group = "me.kvdpxne"
  version = "0.1.0"
}

subprojects {

  apply {
    plugin("java")
    plugin("maven-publish")
    plugin("com.gradleup.shadow")
  }

  dependencies {
    // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
    implementation("org.apache.commons:commons-lang3:3.17.0")
  }

  val targetJavaVersion = 8

  java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)

    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion

    if (JavaVersion.current() < javaVersion) {
      toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    }
  }

  afterEvaluate {

    publishing {
      publications {
        register("mavenJava", MavenPublication::class) {
          from(components["java"])
        }
      }
    }
  }

  tasks {

    withType<JavaCompile> {
      if (10 <= targetJavaVersion || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
      }

      options.compilerArgs.add("-Xlint:-options")
    }

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