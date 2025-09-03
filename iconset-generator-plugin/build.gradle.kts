plugins {
    id("java-library")
    kotlin("jvm") version "2.2.10"
    id("java-gradle-plugin")
    id("com.gradle.plugin-publish") version "2.0.0"
    signing
}

group = "io.github.devdiestrolopez.iconset.generator"
version = "1.1.0"

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
    website.set("https://github.com/devdiestrolopez/icon-set-generator")
    vcsUrl.set("https://github.com/devdiestrolopez/icon-set-generator")
    plugins {
        register("IconSetGeneratorPlugin") {
            id = "io.github.devdiestrolopez.iconset.generator"
            implementationClass = "io.github.devdiestrolopez.iconsetgenerator.plugin.IconSetGeneratorPlugin"
            displayName = "IconSet Generator Plugin"
            description = "A Gradle plugin for Android that auto-generates a type-safe IconSet from your project's vector drawables. It leverages " +
                    "the icon-core-android library for a unified and robust approach to resource handling."
            tags = listOf("android", "kotlin", "generation", "kotlin-dsl", "iconset")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

dependencies {
    implementation("com.squareup:kotlinpoet:2.2.0")
    implementation("androidx.annotation:annotation:1.9.1")
    compileOnly("com.android.tools.build:gradle:8.12.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
}
