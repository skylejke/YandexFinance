package ru.point.settings.ui.appinfo.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.api.repository.SettingsRepository
import ru.point.ui.MviViewModel
import javax.inject.Inject

internal class AppInfoViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<AppInfoState, AppInfoAction, Any>(initialState = AppInfoState()) {

    init {
        getAppVersion()
        getLastTimeUpdate()
    }

    override fun reduce(action: AppInfoAction, state: AppInfoState) =
        when (action) {

            is AppInfoAction.OnGetAppVersion -> state.copy(appVersion = action.appVersion)

            is AppInfoAction.OnGetLastTimeUpdate -> state.copy(lastTimeUpdate = action.lastTimeUpdate)
        }

    private fun getAppVersion() {
        viewModelScope.launch {
            onAction(AppInfoAction.OnGetAppVersion(settingsRepository.getAppVersion()))
        }
    }

    private fun getLastTimeUpdate() {
        viewModelScope.launch {
            onAction(AppInfoAction.OnGetLastTimeUpdate(settingsRepository.getAppLastTimeUpdate()))
        }
    }
}