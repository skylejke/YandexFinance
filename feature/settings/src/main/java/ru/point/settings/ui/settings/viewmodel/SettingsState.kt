package ru.point.settings.ui.settings.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
internal data class SettingsState(
    val isDarkThemeEnabled: Boolean = false,
)
