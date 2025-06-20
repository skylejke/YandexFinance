package ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel

import ru.point.yandexfinance.feature.transactions.model.Expense

sealed interface ExpensesAction {
    object LoadRequested : ExpensesAction
    data class LoadSuccess(val expenses: List<Expense>) : ExpensesAction
    data class LoadError(val message: String) : ExpensesAction
}