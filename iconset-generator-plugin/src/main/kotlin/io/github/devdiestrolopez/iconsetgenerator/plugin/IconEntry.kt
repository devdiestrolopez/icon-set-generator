package io.github.devdiestrolopez.iconsetgenerator.plugin

internal sealed interface IconEntry {

    val name: String

    data class ResourceEntry(
        override val name: String,
        val resource: String,
    ) : IconEntry

    data class MaterialEntry(
        override val name: String,
        val reference: String,
    ) : IconEntry
}
