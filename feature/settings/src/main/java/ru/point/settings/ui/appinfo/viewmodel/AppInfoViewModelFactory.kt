package ru.point.settings.ui.appinfo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.settings.domain.usecase.GetAppLastTimeUpdateUseCase
import ru.point.settings.domain.usecase.GetAppVersionUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class AppInfoViewModelFactory @Inject constructor(
    private val getAppVersionUseCase: GetAppVersionUseCase,
    private val getAppLastTimeUpdateUseCase: GetAppLastTimeUpdateUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AppInfoViewModel(
            getAppVersionUseCase = getAppVersionUseCase,
            getAppLastTimeUpdateUseCase = getAppLastTimeUpdateUseCase,
        ) as T
}