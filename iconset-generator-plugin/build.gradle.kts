plugins {
    id("java-library")
    id("java-gradle-plugin")
    kotlin("jvm") version "2.2.10"
}

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}

gradlePlugin {
    plugins {
        register("IconSetGeneratorPlugin") {
            id = "es.devdiestrolopez.iconset.generator"
            implementationClass = "es.devdiestrolopez.iconsetgenerator.plugin.IconSetGeneratorPlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())
    implementation("com.squareup:kotlinpoet:2.2.0")
    implementation("androidx.annotation:annotation:1.9.1")
    compileOnly("com.android.tools.build:gradle:8.12.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
}
