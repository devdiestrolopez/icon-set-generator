# IconSetGenerator Gradle Plugin
The IconSetGenerator Gradle plugin streamlines the management of drawable resources in your Kotlin projects. It automatically scans your project's `res/drawable` directory for XML files, specifically those prefixed with `ic_`, and generates a single, type-safe Kotlin object. This object contains properties for each icon, providing compile-time safety and eliminating the need for manual resource declarations.

## ✨ Features
- **Automatic Scanning:** Finds all XML files starting with `ic_` within the `res/drawable` directory.

- **Type-Safe References:** Generates a Kotlin object with properties for each icon, providing compile-time safety and IDE auto-completion.

- **Seamless Integration:** Integrates directly into the Gradle build process, ensuring generated code is always up-to-date.

- **Customizable:** Configure the output package name and class name to fit your project's needs.

## 🚀 Usage
Using the plugin involves a two-step process.

First, add the plugin to your project-level `build.gradle.kts` file.

```kotlin
// build.gradle.kts (project level)
plugins {
  id("io.github.devdiestrolopez.iconset.generator") version "1.0.0" apply false
}
```

Next, apply the plugin in the `build.gradle.kts` file of the specific module where the icons resources reside and where the files will be generated.

```kotlin
// build.gradle.kts (module level)
plugins {
  id("io.github.devdiestrolopez.iconset.generator")
}
```

Finally, configure the plugin by creating an `iconSet` block. You can customize the package and file name for the generated code. If no configuration is needed, the `iconSet` block can be removed.

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

## ⚙️ How It Works
- The IconSetGeneratorPlugin applies a task that reads all files in your `res/drawable` directory that match the `ic_*.xml` pattern.

- For each icon file (e.g., ic_my_icon.xml), it generates a corresponding property in a new Kotlin file. The property name is derived by removing the `ic_` prefix and `.xml` extension and converting the name to PascalCase (e.g., ic_my_icon.xml becomes MyIcon).

- The generated file is placed in the `build/generated/source/main` directory under the configured package.

- The generated properties are of type `DrawableResource`, which holds the `@DrawableRes` integer ID for the icon. The `DrawableResource` class and the sealed interface `IconResource` that implements are both part of the `icon-core-android` library. This classes are part of the `icon-core-android` library.

## 📦 Generated Output
After running a Gradle build, you will find a generated file similar to the one below in your build directory. You can then use the `IconSet` object directly in your Kotlin code.

```kotlin
// Example generated file following the previous iconSet configuration:
// build/generated/source/main/com/example/app/ui/icons/AppIcons.kt

package com.example.app.ui.icons

object AppIcons {
  val MyIcon: DrawableResource = DrawableResource(id = R.drawable.ic_my_icon)
  val AnotherIcon: DrawableResource = DrawableResource(id = R.drawable.ic_another_icon)
  // ... more icons
}
```

## 👀 Future Scope & Usage
Currently, the plugin's generation capabilities are limited to drawable resources (DrawableResource). Future development will expand this functionality to include support for vector images (ImageVectorResource), which are commonly used with libraries like Material Icons.

## 🤝 Contribution & Feedback
We welcome contributions and feedback! If you find a bug or have an idea for a new feature, please open an issue or submit a pull request on the project's repository.
