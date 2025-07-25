package ru.point.settings.ui.appcolor.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.PrimaryColor

@Immutable
internal data class AppColorState(
    val selectedColor: PrimaryColor = PrimaryColor.Green,
)