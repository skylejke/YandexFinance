package ru.point.settings.ui.appinfo.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.settings.domain.usecase.GetAppLastTimeUpdateUseCase
import ru.point.settings.domain.usecase.GetAppVersionUseCase
import ru.point.ui.MviViewModel
import javax.inject.Inject

internal class AppInfoViewModel @Inject constructor(
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val getAppLastTimeUpdateUseCase: GetAppLastTimeUpdateUseCase,
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
            onAction(AppInfoAction.OnGetAppVersion(getAppVersionUseCase()))
        }
    }

    private fun getLastTimeUpdate() {
        viewModelScope.launch {
            onAction(AppInfoAction.OnGetLastTimeUpdate(getAppLastTimeUpdateUseCase()))
        }
    }
}