package ru.point.settings.ui.appinfo.viewmodel

import androidx.compose.runtime.Immutable

@Immutable
internal sealed interface AppInfoAction {

    data class OnGetLastTimeUpdate(val lastTimeUpdate: String) : AppInfoAction

    data class OnGetAppVersion(val appVersion: String) : AppInfoAction
}