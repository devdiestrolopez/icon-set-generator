plugins {
    id("java-library")
    kotlin("jvm") version "2.2.10"
    id("com.gradle.plugin-publish") version "1.3.1"
    signing
}

group = "es.devdiestrolopez.iconset.generator"
version = "1.0.0"

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
    website.set("https://github.com/K4rLiToX/icon-set-generator")
    vcsUrl.set("https://github.com/K4rLiToX/icon-set-generator")
    plugins {
        register("IconSetGeneratorPlugin") {
            id = "es.devdiestrolopez.iconset.generator"
            implementationClass = "es.devdiestrolopez.iconsetgenerator.plugin.IconSetGeneratorPlugin"
            displayName = "IconSet Generator Plugin"
            description = "A Gradle plugin for Android that auto-generates a type-safe IconSet from your project's vector drawables."
            tags = listOf("android", "kotlin", "generation", "kotlin-dsl", "iconset")
        }
    }
}

signing {
    useGpgCmd()
    sign(publishing.publications)
}

dependencies {
    implementation(gradleApi())
    implementation("com.squareup:kotlinpoet:2.2.0")
    implementation("androidx.annotation:annotation:1.9.1")
    compileOnly("com.android.tools.build:gradle:8.12.0")
    compileOnly("org.jetbrains.kotlin:kotlin-gradle-plugin:2.2.10")
}
