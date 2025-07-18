package ru.point.transactions.ui.analysis.viewmodel

import ru.point.transactions.domain.model.AnalysisCategories
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError

internal data class AnalysisState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val startDate: String = "",
    val endDate: String = "",
    val transactions: List<AnalysisCategories> = emptyList(),
) {

    val totalAmount: String
        get() {
            if (transactions.isEmpty()) return ""
            return transactions.sumOf { it.amount.toAmountInt() }
                .toFormattedCurrency(transactions.first().currency)
        }
}
