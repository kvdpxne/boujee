dependencies {
  shadow(project(":p-api"))
  shadow(project(":p-core"))
  shadow(project(":p-inputs:common"))

  testImplementation(project(":p-api"))

  shadow(libraries.gson)
}