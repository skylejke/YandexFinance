package ru.point.transactions.ui.analysis.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.extensions.toAmountInt
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.AppError
import ru.point.vo.AnalysisCategory

@Immutable
internal data class AnalysisState(
    val isLoading: Boolean = true,
    val error: AppError? = null,
    val startDate: String = "",
    val endDate: String = "",
    val transactions: List<AnalysisCategory> = emptyList(),
) {

    val totalAmount: String
        get() {
            if (transactions.isEmpty()) return ""
            return transactions.sumOf { it.amount.toAmountInt() }
                .toFormattedCurrency(transactions.first().currency)
        }
}
