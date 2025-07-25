package ru.point.transactions.ui.editor.viewmodel.state

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError

@Immutable
internal data class FormState(
    val isInitialLoading: Boolean = false,
    val error: AppError? = null,
    val accountName: String = "",
    val categoryId: Int? = null,
    val categoryName: String = "",
    val amountValue: String = "",
    val currency: String = "",
    val transactionDate: String = "",
    val transactionTime: String = "",
    val comment: String? = null,
    val isActionLoading: Boolean = false,
)