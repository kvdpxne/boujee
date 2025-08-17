dependencies {
  implementation(project(":api"))
  implementation(project(":minecraft-api"))
  implementation(project(":core"))
  implementation(project(":singleton-java"))
  implementation(project(":inputs-common"))
  implementation(project(":inputs-json-gson"))

  implementation(libraries.gson)
}