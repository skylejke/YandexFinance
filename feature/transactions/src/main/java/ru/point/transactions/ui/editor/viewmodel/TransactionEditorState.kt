package ru.point.transactions.ui.editor.viewmodel

import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

internal data class TransactionEditorState(
    val accountName: String = "",
    val categoryName: String = "",
    val amountValue: String = "",
    val currency: String = "",
    val transactionDate: String = "",
    val transactionTime: String = "",
    val comment: String? = "",
    val categoriesList: List<CategoryVo> = emptyList(),
    val isLoading: Boolean = false,
    val error: AppError? = null,
)
