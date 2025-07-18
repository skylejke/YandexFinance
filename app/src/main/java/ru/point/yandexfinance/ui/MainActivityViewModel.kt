package ru.point.yandexfinance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.api.model.AccountDto
import ru.point.database.repo.LocalDatabaseRepository

class MainActivityViewModel (
    private val localDatabaseRepository: LocalDatabaseRepository,
) : ViewModel() {

    init {
        prefetchAccounts()
        prefetchUsersCategories()
        prefetchTransactions()
        prefetchCategoriesByType(isIncome = true)
        prefetchCategoriesByType(isIncome = false)
    }

    private fun prefetchAccounts() {
        viewModelScope.launch {
            localDatabaseRepository.prefetchAccounts().onSuccess { it.firstOrNull() ?: AccountDto() }
        }
    }

    private fun prefetchTransactions() {
        viewModelScope.launch {
            localDatabaseRepository.prefetchTransactions().onSuccess { it }
        }
    }

    private fun prefetchUsersCategories() {
        viewModelScope.launch {
            localDatabaseRepository.prefetchUsersCategories().onSuccess { it }
        }
    }

    private fun prefetchCategoriesByType(isIncome: Boolean) {
        viewModelScope.launch {
            localDatabaseRepository.prefetchCategoriesByType(isIncome = isIncome)
        }
    }
}