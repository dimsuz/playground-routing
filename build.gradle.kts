import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
  application
  kotlin("jvm") version "1.3.50"
}

group = "com.github.dimsuz"
version = "1.0-SNAPSHOT"

repositories {
  mavenCentral()
}

application {
  mainClassName = "com.github.dimsuz.playground.routing.MainKt"
}

dependencies {
  implementation(kotlin("stdlib-jdk8"))
}

tasks.withType<KotlinCompile> {
  kotlinOptions.jvmTarget = "1.8"
}
