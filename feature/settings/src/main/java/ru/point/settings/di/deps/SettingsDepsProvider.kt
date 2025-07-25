package ru.point.settings.di.deps

interface SettingsDepsProvider {

    val settingsDeps: SettingsDeps

    companion object : SettingsDepsProvider by SettingsDepsStore
}