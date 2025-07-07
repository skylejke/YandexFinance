package ru.point.transactions.ui.expenses.viewmodel

import ru.point.transactions.domain.model.Expense
import ru.point.utils.model.AppError

internal sealed interface ExpensesAction {
    data object LoadRequested : ExpensesAction
    data class LoadSuccess(val expenses: List<Expense>) : ExpensesAction
    data class LoadError(val error: AppError) : ExpensesAction
}