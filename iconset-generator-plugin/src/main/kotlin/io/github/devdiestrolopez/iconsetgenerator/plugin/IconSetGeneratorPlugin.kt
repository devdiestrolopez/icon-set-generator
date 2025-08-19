package io.github.devdiestrolopez.iconsetgenerator.plugin

import com.android.build.gradle.BaseExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

private const val DRAWABLE_DIRECTORY_PATH = "src/main/res/drawable"
private const val DEFAULT_ICON_SET_FILE_NAME = "IconSet"
private const val GENERATED_DIRECTORY_PATH = "generated/source/main"

internal class IconSetGeneratorPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        val iconSetExtension = target.extensions.create("iconSet", IconSetExtension::class.java)
        val iconSetGeneratorTask = target.tasks.register("generateIconSet", GenerateIconSetTask::class.java)

        target.afterEvaluate { project ->
            val androidExtension = project.extensions.getByType(BaseExtension::class.java)
            val mainSourceSet = androidExtension.sourceSets.getByName("main")

            iconSetExtension.apply {
                outputPackage.convention(androidExtension.namespace!!)
                fileName.convention(DEFAULT_ICON_SET_FILE_NAME)
            }

            val generatedSourceDir = project.layout.buildDirectory.dir(GENERATED_DIRECTORY_PATH)

            iconSetGeneratorTask.configure { task ->
                task.apply {
                    appPackageName.set(androidExtension.namespace!!)
                    drawableDirectory.set(project.layout.projectDirectory.dir(DRAWABLE_DIRECTORY_PATH))
                    outputDirectory.set(generatedSourceDir)
                    outputPackage.set(iconSetExtension.outputPackage.get())
                    fileName.set(iconSetExtension.fileName.get())
                }
            }

            mainSourceSet.java.srcDir(generatedSourceDir)

            project.tasks.withType(KotlinCompile::class.java).configureEach {
                it.dependsOn(iconSetGeneratorTask)
            }
        }
    }
}
