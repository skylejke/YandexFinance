package ru.point.yandexfinance.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.point.api.model.AccountDto
import ru.point.api.repository.SettingsRepository
import ru.point.database.repo.LocalDatabaseRepository
import ru.point.utils.model.PrimaryColor

class MainActivityViewModel(
    private val localDatabaseRepository: LocalDatabaseRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private val _initialDataCollected = MutableStateFlow(false)
    val initialDataCollected get() = _initialDataCollected.asStateFlow()

    private val _isDarkThemeEnabled = MutableStateFlow(false)
    val isDarkThemeEnabled get() = _isDarkThemeEnabled.asStateFlow()

    private val _primaryColor = MutableStateFlow(PrimaryColor.Green)
    val primaryColor get() = _primaryColor.asStateFlow()

    init {
        viewModelScope.launch {
            _isDarkThemeEnabled.value = settingsRepository.isDarkThemeEnabled.first()
            _primaryColor.value = settingsRepository.primaryColor.first()
            observeDarkTheme()
            observePrimaryColor()
            prefetchAll()
            _initialDataCollected.value = true
        }
    }

    private fun observeDarkTheme() {
        viewModelScope.launch {
            settingsRepository.isDarkThemeEnabled
                .collect { _isDarkThemeEnabled.value = it }
        }
    }

    private fun observePrimaryColor() {
        viewModelScope.launch {
            settingsRepository.primaryColor
                .collect { _primaryColor.value = it }
        }
    }

    private fun prefetchAll() {
        prefetchAccounts()
        prefetchUsersCategories()
        prefetchTransactions()
        prefetchCategoriesByType(true)
        prefetchCategoriesByType(false)
    }

    private fun prefetchAccounts() = viewModelScope.launch {
        localDatabaseRepository.prefetchAccounts().onSuccess { it.firstOrNull() ?: AccountDto() }
    }

    private fun prefetchTransactions() = viewModelScope.launch {
        localDatabaseRepository.prefetchTransactions().onSuccess { }
    }

    private fun prefetchUsersCategories() = viewModelScope.launch {
        localDatabaseRepository.prefetchUsersCategories().onSuccess { }
    }

    private fun prefetchCategoriesByType(isIncome: Boolean) = viewModelScope.launch {
        localDatabaseRepository.prefetchCategoriesByType(isIncome)
    }
}