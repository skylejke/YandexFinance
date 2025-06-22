package ru.point.yandexfinance.feature.transactions.ui.expenses.viewmodel

import ru.point.yandexfinance.core.common.model.AppError
import ru.point.yandexfinance.feature.transactions.model.Expense

data class ExpensesState(
    val expenses: List<Expense> = emptyList<Expense>(),
    val isLoading: Boolean = true,
    val error: AppError? = null
)

