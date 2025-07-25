package ru.point.settings.ui.appcolor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.api.repository.SettingsRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class AppColorViewModelFactory @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AppColorViewModel(settingsRepository = settingsRepository) as T
}