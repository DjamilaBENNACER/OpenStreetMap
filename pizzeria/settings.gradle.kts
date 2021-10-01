rootProject.name = "pizzeria"
include("pizzeria-core", "pizzeria-servlet", "pizzeria-vertx", "pizzeria-boot", "pizzeria-webflux")

project(":pizzeria-core").projectDir = File("$rootDir/pizzeria-core")
project(":pizzeria-servlet").projectDir = File("$rootDir/pizzeria-servlet")
project(":pizzeria-vertx").projectDir = File("$rootDir/pizzeria-vertx")
project(":pizzeria-boot").projectDir = File("$rootDir/pizzeria-boot")
project(":pizzeria-webflux").projectDir = File("$rootDir/pizzeria-webflux")

pluginManagement {
  plugins {
    kotlin("jvm") version "${extra["version.kotlin"]}"
    id("org.springframework.boot") version "${extra["version.spring.boot"]}"
    id("com.github.ben-manes.versions") version "${extra["version.versions-plugin"]}"
    id("com.github.johnrengelman.shadow") version "5.2.0"
  }
}
