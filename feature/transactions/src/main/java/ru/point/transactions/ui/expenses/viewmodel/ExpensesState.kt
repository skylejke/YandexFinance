package ru.point.transactions.ui.expenses.viewmodel

import ru.point.transactions.domain.model.Expense
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

internal data class ExpensesState(
    val expenses: List<Expense> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null
) {
    val amount get() = expenses.sumOf { it.amount.toAmountInt() }.toFormattedCurrency(expenses.first().currency)
}

