package ru.point.transactions.incomes.ui.viewmodel

import ru.point.transactions.incomes.domain.model.Income
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

data class IncomesState(
    val incomes: List<Income> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null
) {
    val amount get() = incomes.sumOf { it.amount.toAmountInt() }.toFormattedCurrency(incomes.first().currency)
}