package ru.point.settings.ui.settings.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.point.api.repository.SettingsRepository
import ru.point.ui.MviViewModel
import javax.inject.Inject

internal class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<SettingsState, SettingsAction, Any>(initialState = SettingsState()) {

    init {
        observeTheme()
        onHandleToggleTheme()
    }

    override fun reduce(action: SettingsAction, state: SettingsState) =
        when (action) {
            is SettingsAction.OnGetThemeState -> state.copy(isDarkThemeEnabled = action.isDarkThemeEnabled)

            is SettingsAction.OnToggleDarkTheme -> state.copy(isDarkThemeEnabled = action.isDarkThemeEnabled)
        }

    private fun observeTheme() {
        viewModelScope.launch {
            settingsRepository.isDarkThemeEnabled
                .collectLatest { isDarkThemeEnabled ->
                    onAction(SettingsAction.OnGetThemeState(isDarkThemeEnabled))
                }
        }
    }

    private fun onHandleToggleTheme() {
        handleAction<SettingsAction.OnToggleDarkTheme> { action ->
            settingsRepository.toggleDarkTheme(action.isDarkThemeEnabled)
        }
    }
}