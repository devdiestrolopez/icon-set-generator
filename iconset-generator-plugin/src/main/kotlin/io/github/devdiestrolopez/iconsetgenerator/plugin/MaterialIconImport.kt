package io.github.devdiestrolopez.iconsetgenerator.plugin

/**
 * Represents a Material Icon import.
 *
 * @property packageName The package name of the Material Icon (e.g., androidx.compose.material.icons.filled).
 * @property iconName The name of the Material Icon (e.g., Home).
 */
data class MaterialIconImport(
    val packageName: String,
    val iconName: String,
)
