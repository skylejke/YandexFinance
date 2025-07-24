package ru.point.settings.di.deps

import kotlin.properties.Delegates.notNull

object SettingsDepsStore : SettingsDepsProvider {

    override var settingsDeps: SettingsDeps by notNull()
}