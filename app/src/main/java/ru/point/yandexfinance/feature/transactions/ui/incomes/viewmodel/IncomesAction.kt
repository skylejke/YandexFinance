package ru.point.yandexfinance.feature.transactions.ui.incomes.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.transactions.model.Income

sealed interface IncomesAction {
    data object LoadRequested : IncomesAction
    data class LoadSuccess(val incomes: List<Income>) : IncomesAction
    data class LoadError(val error: AppError) : IncomesAction
}