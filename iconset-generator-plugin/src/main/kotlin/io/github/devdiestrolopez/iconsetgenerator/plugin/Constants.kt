package io.github.devdiestrolopez.iconsetgenerator.plugin

import com.squareup.kotlinpoet.ClassName

internal object Constants {
    const val ICON_CORE_ANDROID_PACKAGE = "io.github.devdiestrolopez.icon.core.android"
    const val ICON_PREFIX = "ic_"
    const val XML_EXTENSION = "xml"
    val MaterialIconRegex = Regex("^val\\s(\\w+)\\s=\\s(.*)")
    val MaterialIconImportRegex = Regex("^import\\s(androidx\\.compose\\.material\\.icons.*)")
    val DrawableResourceClassName = ClassName(ICON_CORE_ANDROID_PACKAGE, "DrawableResource")
    val ImageVectorResourceClassName = ClassName(ICON_CORE_ANDROID_PACKAGE, "ImageVectorResource")
}
