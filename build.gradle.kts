import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  application
  id("org.springframework.boot") version "2.2.1.RELEASE"
  id("io.spring.dependency-management") version "1.0.8.RELEASE"
  kotlin("jvm") version "1.3.50"
  kotlin("plugin.spring") version "1.3.50"
}

group = "com.github.dimsuz"
version = "1.0-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
  mavenCentral()
}

application {
  mainClassName = "com.github.dimsuz.playground.routing.MainKt"
}

dependencies {
  implementation("org.springframework.boot:spring-boot-starter")
  implementation("org.springframework.statemachine:spring-statemachine-starter")
  implementation(kotlin("reflect"))
  implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
  kotlinOptions.freeCompilerArgs = listOf("-Xjsr305=strict")
}

dependencyManagement {
  imports {
    mavenBom("org.springframework.statemachine:spring-statemachine-bom:2.1.3.RELEASE")
  }
}
