package es.devdiestrolopez.iconsetgenerator.plugin

import org.gradle.api.provider.Property

interface IconSetExtension {

    val outputPackage: Property<String>
    val fileName: Property<String>
}
