package ru.point.settings.ui.appcolor.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.point.api.repository.SettingsRepository
import ru.point.ui.MviViewModel
import javax.inject.Inject

internal class AppColorViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : MviViewModel<AppColorState, AppColorAction, Any>(initialState = AppColorState()) {

    init {
        observePrimaryColor()
        onHandleTogglePrimaryColor()
    }

    override fun reduce(action: AppColorAction, state: AppColorState) =
        when (action) {
            is AppColorAction.OnGetPrimaryColor -> state.copy(selectedColor = action.primaryColor)

            is AppColorAction.OnTogglePrimaryColor -> state.copy(selectedColor = action.primaryColor)
        }

    private fun observePrimaryColor() {
        viewModelScope.launch {
            settingsRepository.primaryColor
                .collectLatest { primaryColor ->
                    onAction(AppColorAction.OnGetPrimaryColor(primaryColor))
                }
        }
    }

    private fun onHandleTogglePrimaryColor() {
        handleAction<AppColorAction.OnTogglePrimaryColor> { action ->
            settingsRepository.togglePrimaryColor(action.primaryColor)
        }
    }
}