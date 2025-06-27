package ru.point.transactions.expenses.ui.viewmodel

import ru.point.transactions.expenses.domain.model.Expense
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

data class ExpensesState(
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null
) {
    val amount get() = expenses.sumOf { it.amount.toAmountInt() }.toFormattedCurrency(expenses.first().currency)
}

