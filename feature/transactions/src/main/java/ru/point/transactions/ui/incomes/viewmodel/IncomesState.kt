package ru.point.transactions.ui.incomes.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.transactions.domain.model.Income
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

@Immutable
internal data class IncomesState(
    val incomes: List<Income> = emptyList(),
    val isLoading: Boolean = true,
    val error: AppError? = null
) {
    val amount get() = incomes.sumOf { it.amount.toAmountInt() }.toFormattedCurrency(incomes.first().currency)
}