package ru.point.settings.ui.appinfo.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
internal data class AppInfoState(
    val appVersion: String = "",
    val lastTimeUpdate: String = "",
)
