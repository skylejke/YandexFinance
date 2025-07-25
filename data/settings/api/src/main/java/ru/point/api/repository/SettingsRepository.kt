package ru.point.api.repository

import kotlinx.coroutines.flow.Flow
import ru.point.utils.model.PrimaryColor

interface SettingsRepository {

    fun getAppVersion(): String

    fun getAppLastTimeUpdate(): String

    val isDarkThemeEnabled: Flow<Boolean>

    val primaryColor: Flow<PrimaryColor>

    suspend fun toggleDarkTheme(isDarkThemeEnabled: Boolean)

    suspend fun togglePrimaryColor(primaryColor: PrimaryColor)
}