package ru.point.transactions.ui.editor.viewmodel

import ru.point.transactions.domain.model.TransactionVo
import ru.point.utils.model.AppError
import ru.point.vo.CategoryVo

internal sealed interface TransactionEditorAction {

    data object TransactionLoadRequested : TransactionEditorAction

    data class TransactionLoadSuccess(val transactionVo: TransactionVo) : TransactionEditorAction

    data class TransactionLoadError(val error: AppError) : TransactionEditorAction

    data object CategoriesLoadRequested : TransactionEditorAction

    data class CategoriesLoadSuccess(val categories: List<CategoryVo>) : TransactionEditorAction

    data class CategoriesLoadError(val error: AppError) : TransactionEditorAction

    data class OnAmountChange(val amount: String) : TransactionEditorAction

    data class OnDateChange(val date: String) : TransactionEditorAction

    data class OnTimeChange(val time: String) : TransactionEditorAction

    data class OnCategoryChange(val category: String) : TransactionEditorAction

    data class OnCommentChanged(val comment: String) : TransactionEditorAction
}