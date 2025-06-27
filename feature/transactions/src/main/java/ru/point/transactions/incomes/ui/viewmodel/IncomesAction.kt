package ru.point.transactions.incomes.ui.viewmodel

import ru.point.transactions.incomes.domain.model.Income
import ru.point.utils.model.AppError

sealed interface IncomesAction {
    data object LoadRequested : IncomesAction
    data class LoadSuccess(val incomes: List<Income>) : IncomesAction
    data class LoadError(val error: AppError) : IncomesAction
}