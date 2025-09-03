package io.github.devdiestrolopez.iconsetgenerator.plugin

/**
 * Represents an entry for an icon.
 * This can be either a resource-based icon or a Material Design icon.
 */
internal sealed interface IconEntry {

    val name: String

    /**
     * Represents a resource entry for an icon.
     *
     * This data class holds information about an icon that is sourced from a resource.
     *
     * @property name The name of the icon entry. This will be used as the identifier for the generated icon.
     * @property resource The path or identifier of the resource file (e.g., an XML file) for the icon.
     */
    data class ResourceEntry(
        override val name: String,
        val resource: String,
    ) : IconEntry

    /**
     * Represents a material icon entry.
     *
     * @property name The name of the icon.
     * @property reference The reference of the material icon (e.g., Icons.Style.IconName).
     */
    data class MaterialEntry(
        override val name: String,
        val reference: String,
    ) : IconEntry
}
