package es.devdiestrolopez.iconsetgenerator.plugin

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File

private const val PLUGIN_PACKAGE_NAME = "es.devdiestrolopez.iconset.generator.plugin"
private const val ICON_PREFIX = "ic_"
private const val XML_EXTENSION = "xml"

abstract class GenerateIconSetTask : DefaultTask() {

    @get:Input
    abstract val appPackageName: Property<String>

    @get:InputDirectory
    abstract val drawableDirectory: DirectoryProperty

    @get:OutputDirectory
    abstract val outputDirectory: DirectoryProperty

    @get:Input
    abstract val outputPackage: Property<String>

    @get:Input
    abstract val fileName: Property<String>

    @TaskAction
    fun generate() {
        val drawableFiles = drawableDirectory.asFileTree.filter {
            it.name.startsWith(ICON_PREFIX) && it.extension == XML_EXTENSION
        }

        val drawableResourceClassName = ClassName(PLUGIN_PACKAGE_NAME, "DrawableResource")

        val propertySpecs = drawableFiles.map { file ->
            createPropertySpec(file, drawableResourceClassName)
        }

        val iconSetObject = TypeSpec
            .objectBuilder(fileName.get())
            .addProperties(propertySpecs)
            .build()

        val fileSpec = FileSpec.builder(outputPackage.get(), fileName.get())
            .addImport(appPackageName.get(), "R")
            .addType(iconSetObject)
            .build()

        fileSpec.writeTo(outputDirectory.asFile.get())
    }

    private fun createPropertySpec(file: File, resourceClassName: ClassName): PropertySpec {
        val iconName = file.nameWithoutExtension
            .removePrefix(ICON_PREFIX)
            .split("_")
            .joinToString("") { it.replaceFirstChar(Char::uppercase) }

        return PropertySpec.builder(iconName, resourceClassName)
            .initializer("%T(%L)", resourceClassName, "R.drawable.${file.nameWithoutExtension}")
            .build()
    }
}
