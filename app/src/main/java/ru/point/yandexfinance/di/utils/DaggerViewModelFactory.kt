package ru.point.yandexfinance.di.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

/**
 * Фабрика ViewModel'ей для использования с Dagger 2.
 *
 * Позволяет создавать экземпляры ViewModel с поддержкой внедрения зависимостей.
 * Ищет подходящий [javax.inject.Provider] в карте [creators] по классу ViewModel или его суперклассу.
 *
 * Используется в связке с multibindings для регистрации всех ViewModel в графе зависимостей.
 */
class DaggerViewModelFactory @Inject constructor(
    private val creators: Map<@JvmSuppressWildcards Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val provider = creators[modelClass]
            ?: creators.entries.firstOrNull { modelClass.isAssignableFrom(it.key) }?.value
            ?: throw IllegalArgumentException("Unknown ViewModel class: $modelClass")
        @Suppress("UNCHECKED_CAST")
        return provider.get() as T
    }
}