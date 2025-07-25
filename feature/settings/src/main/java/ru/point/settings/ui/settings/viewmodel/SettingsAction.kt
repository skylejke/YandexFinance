package ru.point.settings.ui.settings.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface SettingsAction {

    data class OnGetThemeState(val isDarkThemeEnabled: Boolean) : SettingsAction

    data class OnToggleDarkTheme(val isDarkThemeEnabled: Boolean) : SettingsAction
}