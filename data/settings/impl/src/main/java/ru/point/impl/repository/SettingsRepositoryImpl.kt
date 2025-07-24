package ru.point.impl.repository

import android.content.Context
import ru.point.api.repository.SettingsRepository
import ru.point.utils.extensions.fullDateFormatter
import ru.point.utils.extensions.getAppVersionName
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

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
}