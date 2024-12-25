dependencies {
  shadow(project(":api"))
  shadow(project(":core"))
  shadow(project(":inputs-common"))

  testImplementation(project(":api"))

  shadow(libraries.gson)
}