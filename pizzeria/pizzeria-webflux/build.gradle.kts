plugins {
  java
  id("org.springframework.boot")
}

tasks.bootJar {
  archiveName = "pizzeria-webflux.jar"
  manifest.attributes["Implementation-Version"] = project.version
}

dependencies {
  compile(project(":pizzeria-core"))
  implementation("org.springframework.boot:spring-boot-configuration-processor")
  implementation("org.springframework.boot:spring-boot-starter-jdbc")
  implementation("org.springframework.boot:spring-boot-starter-data-jpa")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
}
