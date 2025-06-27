package ru.point.transactions.expenses.ui.viewmodel

import ru.point.transactions.expenses.domain.model.Expense
import ru.point.utils.model.AppError

sealed interface ExpensesAction {
    data object LoadRequested : ExpensesAction
    data class LoadSuccess(val expenses: List<Expense>) : ExpensesAction
    data class LoadError(val error: AppError) : ExpensesAction
}