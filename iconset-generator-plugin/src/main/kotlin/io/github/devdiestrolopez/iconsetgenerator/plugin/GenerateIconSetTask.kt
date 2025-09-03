package io.github.devdiestrolopez.iconsetgenerator.plugin

import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.work.NormalizeLineEndings
import java.io.File

internal abstract class GenerateIconSetTask : DefaultTask() {

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

    @get:InputFile
    @get:Optional
    @get:NormalizeLineEndings
    abstract val materialIconsSourceFile: RegularFileProperty

    @TaskAction
    fun generate() {
        val drawableIcons = createDrawableIcons()
        val materialIconsFile = materialIconsSourceFile.asFile.orNull
        val (materialIconsImports, materialIcons) = if (materialIconsFile == null || !materialIconsFile.exists()) {
            logger.warn("Material Icon file was not found at [src/icons/kotlin].")
            emptyList<MaterialIconImport>() to emptyList()
        } else {
            materialIconsFile.parseMaterialIconsFile()
        }

        if (drawableIcons.isEmpty() && materialIcons.isEmpty()) {
            logger.warn("Skipping icon set generation.")
            return
        }

        val icons = (drawableIcons + materialIcons).sortedBy { icon -> icon.name }
        val propertySpecs = icons.map { icon ->
            createPropertySpec(icon)
        }

        val iconSetObject = TypeSpec
            .objectBuilder(fileName.get())
            .addProperties(propertySpecs)
            .build()

        val fileSpec = FileSpec.builder(outputPackage.get(), fileName.get())
            .addImport(appPackageName.get(), "R")
            .also { builder ->
                if (materialIconsImports.isNotEmpty()) {
                    materialIconsImports.forEach { import ->
                        builder.addImport(import.packageName, import.iconName)
                    }
                }
            }
            .addType(iconSetObject)
            .build()

        fileSpec.writeTo(outputDirectory.asFile.get())
    }

    private fun createDrawableIcons(): List<IconEntry> {
        val drawableFiles = drawableDirectory.asFileTree.filter {
            it.name.startsWith(Constants.ICON_PREFIX) && it.extension == Constants.XML_EXTENSION
        }
        return if (drawableFiles.isEmpty) {
            logger.info("No drawable icons found in [${drawableDirectory.get().asFile.absolutePath}].")
            emptyList()
        } else {
            drawableFiles.map { file ->
                val iconName = file.nameWithoutExtension
                    .removePrefix(Constants.ICON_PREFIX)
                    .split("_")
                    .joinToString("") { it.replaceFirstChar(Char::uppercase) }
                val resource = "R.drawable.${file.nameWithoutExtension}"
                IconEntry.ResourceEntry(iconName, resource)
            }
        }
    }

    private fun File.parseMaterialIconsFile(): Pair<List<MaterialIconImport>, List<IconEntry>> {
        val lines = readLines().asSequence()

        val imports = lines
            .mapNotNull { line ->
                val matchResult = Constants.MaterialIconImportRegex.find(line)
                matchResult?.groupValues?.get(1)
            }
            .map { importPath ->
                val segments = importPath.split(".")
                val packageName = segments.dropLast(1).joinToString(".")
                val iconName = segments.last()
                MaterialIconImport(packageName, iconName)
            }.toList()

        val icons = lines
            .mapNotNull { line ->
                val matchResult = Constants.MaterialIconRegex.find(line)
                matchResult?.destructured
            }
            .map { (iconName, reference) ->
                IconEntry.MaterialEntry(iconName, reference)
            }.toList()

        return imports to icons
    }

    private fun createPropertySpec(icon: IconEntry): PropertySpec {
        val type = when (icon) {
            is IconEntry.ResourceEntry -> Constants.DrawableResourceClassName
            is IconEntry.MaterialEntry -> Constants.ImageVectorResourceClassName
        }
        val reference = when (icon) {
            is IconEntry.ResourceEntry -> icon.resource
            is IconEntry.MaterialEntry -> icon.reference
        }
        val initializer = CodeBlock.of("%T(%L)", type, reference)
        return PropertySpec.builder(icon.name, type)
            .initializer(initializer)
            .build()
    }
}
