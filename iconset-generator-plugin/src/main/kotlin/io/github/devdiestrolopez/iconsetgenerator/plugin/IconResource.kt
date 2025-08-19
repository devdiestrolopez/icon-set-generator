package io.github.devdiestrolopez.iconsetgenerator.plugin

import androidx.annotation.DrawableRes

sealed interface IconResource
data class DrawableResource(@DrawableRes val id: Int) : IconResource
