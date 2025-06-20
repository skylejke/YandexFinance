package ru.point.yandexfinance.feature.transactions.ui.incomes.viewmodel

import ru.point.yandexfinance.feature.transactions.model.Income

sealed interface IncomesAction {
    data object LoadRequested : IncomesAction
    data class LoadSuccess(val incomes: List<Income>) : IncomesAction
    data class LoadError(val message: String) : IncomesAction
}