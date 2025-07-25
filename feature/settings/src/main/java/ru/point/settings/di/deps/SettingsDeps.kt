package ru.point.settings.di.deps

import ru.point.api.repository.SettingsRepository

interface SettingsDeps {

    val settingsRepository: SettingsRepository
}