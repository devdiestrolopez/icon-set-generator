package io.github.devdiestrolopez.iconsetgenerator.plugin

import com.squareup.kotlinpoet.ClassName

internal object ProjectConstants {
    private const val ICONS_SOURCE_DIRECTORY_PATH = "src/icons/kotlin"
    private const val MATERIAL_ICONS_FILE = "MaterialIcons.kt"
    const val ICON_PREFIX = "ic_"
    const val XML_EXTENSION = "xml"
    const val DRAWABLE_DIRECTORY_PATH = "src/main/res/drawable"
    const val DEFAULT_ICON_SET_FILE_NAME = "IconSet"
    const val GENERATED_DIRECTORY_PATH = "generated/source/main"
    const val MATERIAL_ICONS_FILE_PATH = "$ICONS_SOURCE_DIRECTORY_PATH/$MATERIAL_ICONS_FILE"
}

internal object ClassNames {
    private const val ICON_CORE_ANDROID_PACKAGE = "io.github.devdiestrolopez.icon.core.android"
    val DrawableResource = ClassName(ICON_CORE_ANDROID_PACKAGE, "DrawableResource")
    val ImageVectorResource = ClassName(ICON_CORE_ANDROID_PACKAGE, "ImageVectorResource")
}

internal object RegexPatterns {
    val MaterialIconVariable = """
        ^val\s(\w+)\s=\s(.*)
    """.trimIndent().toRegex()
    val MaterialIconImport = """
        ^import\s(androidx\.compose\.material\.icons.*)
    """.trimIndent().toRegex()
}
