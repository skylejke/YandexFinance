package ru.point.transactions.ui.incomes.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.transactions.domain.model.Income
import ru.point.utils.model.AppError

@Immutable
internal sealed interface IncomesAction {
    data object LoadRequested : IncomesAction
    data class LoadSuccess(val incomes: List<Income>) : IncomesAction
    data class LoadError(val error: AppError) : IncomesAction
}