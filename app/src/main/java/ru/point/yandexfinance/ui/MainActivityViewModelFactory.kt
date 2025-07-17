package ru.point.yandexfinance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.database.repo.LocalDatabaseRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory @Inject constructor(
    private val localDatabaseRepository: LocalDatabaseRepository,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        MainActivityViewModel(localDatabaseRepository = localDatabaseRepository) as T
}