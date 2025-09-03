package io.github.devdiestrolopez.iconsetgenerator.plugin

import org.gradle.api.provider.Property

/**
 * Defines the configuration options for the IconSetGenerator plugin.
 * This interface allows users to customize the output package and file name
 * for the generated icon set.
 */
interface IconSetExtension {

    val outputPackage: Property<String>
    val fileName: Property<String>
}
