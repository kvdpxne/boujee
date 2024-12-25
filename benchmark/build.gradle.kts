plugins {
  id("me.champeau.jmh")
}

dependencies {
  jmh(libraries.jmh.core)
  implementation(project(":core"))

  // https://mvnrepository.com/artifact/org.apache.commons/commons-lang3
  implementation("org.apache.commons:commons-lang3:3.17.0")
}

jmh {
  jmhVersion.set("1.37")
  resultFormat.set("CSV")
}