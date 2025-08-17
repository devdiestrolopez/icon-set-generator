package es.devdiestrolopez.iconsetgenerator.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetContainer
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.io.File

private const val DRAWABLE_DIRECTORY_PATH = "src/main/res/drawable"
private const val DEFAULT_ICON_SET_FILE_NAME = "IconSet"
private const val DEFAULT_ICON_SET_PACKAGE_NAME = ".ui.iconSet"

class IconSetGeneratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val iconSetExtension = target.extensions.create("iconSet", IconSetExtension::class.java)
        val iconSetGeneratorTask = target.tasks.register("generateIconSet", GenerateIconSetTask::class.java)

        target.afterEvaluate { project ->
            val androidExtension = project.extensions.getByType(BaseExtension::class.java)
            val mainSourceSet = androidExtension.sourceSets.getByName("main")

            iconSetExtension.apply {
                outputPackage.convention(androidExtension.namespace!! + DEFAULT_ICON_SET_PACKAGE_NAME)
                fileName.convention(DEFAULT_ICON_SET_FILE_NAME)
            }

            val kotlinSourceSets = project.extensions.getByType(KotlinSourceSetContainer::class.java)
            val sourceDirs = kotlinSourceSets.sourceSets.getByName("main").kotlin.srcDirs + mainSourceSet.java.srcDirs
            val sourceDir =
                sourceDirs.firstOrNull(File::exists) ?: throw IllegalStateException("Could not find a main source directory for Java or Kotlin.")

            iconSetGeneratorTask.configure { task ->
                task.apply {
                    appPackageName.set(androidExtension.namespace!!)
                    drawableDirectory.set(project.layout.projectDirectory.dir(DRAWABLE_DIRECTORY_PATH))
                    outputDirectory.set(project.file(sourceDir))
                    outputPackage.set(iconSetExtension.outputPackage.get())
                    fileName.set(iconSetExtension.fileName.get())
                }
            }

            project.tasks.withType(KotlinCompile::class.java).configureEach {
                it.dependsOn(iconSetGeneratorTask)
            }

            mainSourceSet.java.srcDir(iconSetGeneratorTask.get().outputDirectory.asFile)
        }
    }
}
