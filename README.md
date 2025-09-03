![Gradle Plugin Portal](https://img.shields.io/gradle-plugin-portal/v/io.github.devdiestrolopez.iconset.generator?strategy=latestProperty&style=for-the-badge&logo=gradle&logoColor=%23FFFFFF&label=iconset-generator&color=blue&link=https%3A%2F%2Fplugins.gradle.org%2Fplugin%2Fio.github.devdiestrolopez.iconset.generator)


[![Android Gradle Plugin](https://img.shields.io/badge/AGP-8.12.1-blue?style=for-the-badge)](https://developer.android.com/studio/releases/gradle-plugin)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-blue?style=for-the-badge&logo=kotlin&logoColor=orange)](https://kotlinlang.org/docs/whatsnew22.html)

# IconSetGenerator Gradle Plugin
The IconSetGenerator Gradle plugin streamlines the management of icon resources by automatically generating a single, type-safe Kotlin object. This object contains properties for your icons, providing compile-time safety and eliminating the need for manual resource management.

## ✨ Features
- **Automatic Scanning:** Finds both XML files matching the `ic_*.xml` pattern and Material 3 icon variables defined in a `MaterialIcons.kt` file.

- **Type-Safe References:** Generates a Kotlin object with properties for each icon, providing compile-time safety and IDE auto-completion.

- **Seamless Integration:** Integrates directly into the Gradle build process, ensuring generated code is always up-to-date.

- **Customizable:** Configure the output package name and class name to fit your project's needs.

## 🚀 Usage
Using the plugin involves a two-step process.

### 1. Add the Dependencies

```kotlin
// build.gradle.kts (project level)
plugins {
  id("io.github.devdiestrolopez.iconset.generator") version "<latest-version>" apply false
}
```

```kotlin
// build.gradle.kts (module level)
plugins {
  id("io.github.devdiestrolopez.iconset.generator")
}
```

### 2. Configure the Plugin
If no configuration is needed, the `iconSet` block can be removed.

```kotlin
// build.gradle.kts (module level)
iconSet {
  // (Optional) The package where the generated class will be created.
  // By default, this is set to <your_module_package_name>
  outputPackage.set("com.example.app.ui.icons")
  
  // (Optional) The name of the generated Kotlin object.
  // By default, this is set to "IconSet"
  fileName.set("AppIcons")
}
```

> [!WARNING]
> To function correctly, the plugin **must be used with either `icon-core-android` or `icon-compose-android` libraries**. You must add one of these as a dependency to your module's `build.gradle.kts` file. You can find detailed instructions and more information in the [Icon Project](https://github.com/devdiestrolopez/icon)

### Support Material 3 Icons
To be able to generate icons from the Material 3 libraries:

1. Make sure you are using the corresponding Material libraries for the icons that will be used:

- `androidx.compose.material3:material3`

- `androidx.compose.material:material-icons-core`

- `androidx.compose.material:material-icons-extended`

2. Define a new source directory inside the `android {}` block to tell the plugin where to look for your icon definitions:

```kotlin
// build.gradle.kts (module level)
android {
    sourceSets {
        getByName("main") {
            this.kotlin.srcDir("src/icons/kotlin")
        }
    }
}
```

3. Create the `icons/kotlin` directory inside the `src` folder.

4. Sync Gradle to apply the changes.

5. Create a `MaterialIcons.kt` file inside the `src/icons/kotlin` directory you just created.

6. Declare your icons inside the `MaterialIcons.kt` file using first-class variables in the format `val IconName = Icons.Style.IconName`. The variable name will be the name of the generated icon property.

```kotlin
// src/icons/kotlin/MaterialIcons.kt
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Search

val HomeFilled = Icons.Filled.Home
val Search = Icons.Outlined.Search
...
```

> [!IMPORTANT]
> For the plugin to work correctly, all imports must be full and without wildcards.

## 💻 Compatibility
The project is regularly tested with the following technology stack. For a complete list of dependencies and their versions, refer to the plugin's module `build.gradle.kts` file.

| Technology | Version |
| -------- | ------- |
| Android Gradle Plugin (AGP) | 8.12.1 |
| Kotlin | 2.2.10 |
| minSdk | 28 |
| targetSdk | 36 |
| compileSdk | 36 |

## ⚙️ How It Works
- The IconSetGeneratorPlugin applies a task that identifies your icons in two ways:
  - **Drawable Resources:** It read all files in your `res/drawable` directory that match the `ic_*.xml` pattern.
  - **Image Vector Resources:** It read the variables defined in your `src/icons/kotlin/MaterialIcons.kt` file.

- Based on the source, the plugin generates a corresponding property in a new Kotlin file:
  - `ic_*.xml` files are converted to `DrawableResource` properties. The property name is derived by removing the `ic_` prefix and `.xml` extension and converting the name to PascalCase (e.g., ic_my_icon.xml` becomes `MyIcon`).
  - Variables in `MaterialIcons.kt` are converted to `ImageVectorResource` properties, using the variable name defined.
  - This classes extend the sealed interface `IconResource`, being all part of the `icon-core-android` library.

- The generated file is placed in the `build/generated/source/main` directory under the configured package.

## 📦 Generated Output
After running a Gradle build, you will find a generated file similar to the one below in your build directory. You can then use the generated object directly in your Kotlin code.

```kotlin
// Example generated file following the previous iconSet configuration:
// build/generated/source/main/com/example/app/ui/icons/AppIcons.kt

package com.example.app.ui.icons

// Imports

object AppIcons {
  val AnotherIcon: DrawableResource = DrawableResource(id = R.drawable.ic_another_icon)
  val HomeFilled: ImageVectorResource = ImageVectorResource(vector = Icons.Filled.Home)
  val MyIcon: DrawableResource = DrawableResource(id = R.drawable.ic_my_icon)
  val Search: ImageVectorResource = ImageVectorResource(vector = Icons.Outlined.Search)  
}
```

## 🤝 Contribution & Feedback
We welcome contributions and feedback! If you find a bug or have an idea for a new feature, please open an issue or submit a pull request on the project's repository.
