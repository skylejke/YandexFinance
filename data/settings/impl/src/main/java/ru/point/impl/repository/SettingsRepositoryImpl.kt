package ru.point.impl.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import ru.point.api.repository.SettingsRepository
import ru.point.utils.extensions.fullDateFormatter
import ru.point.utils.extensions.getAppVersionName
import ru.point.utils.model.PrimaryColor
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private val Context.themeDataStore by preferencesDataStore(name = "theme_prefs")

private val IS_DARK_THEME_ENABLED_KEY = booleanPreferencesKey("is_dark_theme_enabled")

private val PRIMARY_COLOR_KEY = stringPreferencesKey("primary_color")

internal class SettingsRepositoryImpl(private val context: Context) : SettingsRepository {

    override fun getAppVersion() = context.getAppVersionName()

    override fun getAppLastTimeUpdate(): String {
        val millis = context.packageManager
            .getPackageInfo(context.packageName, 0).lastUpdateTime

        val formatter = fullDateFormatter ?: DateTimeFormatter.ofPattern("dd.MM.yyyy")

        val localDate = Instant.ofEpochMilli(millis)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()

        return formatter.format(localDate)
    }

    override val isDarkThemeEnabled
        get() = context.themeDataStore.data
            .map { prefs -> prefs[IS_DARK_THEME_ENABLED_KEY] ?: false }

    override val primaryColor
        get() = context.themeDataStore.data
            .map { prefs -> PrimaryColor.valueOf(prefs[PRIMARY_COLOR_KEY] ?: PrimaryColor.Green.name) }

    override suspend fun toggleDarkTheme(isDarkThemeEnabled: Boolean) {
        withContext(Dispatchers.IO) {
            context.themeDataStore.edit { prefs ->
                prefs[IS_DARK_THEME_ENABLED_KEY] = isDarkThemeEnabled
            }
        }
    }

    override suspend fun togglePrimaryColor(primaryColor: PrimaryColor) {
        withContext(Dispatchers.IO) {
            context.themeDataStore.edit { prefs ->
                prefs[PRIMARY_COLOR_KEY] = primaryColor.name
            }
        }
    }
}