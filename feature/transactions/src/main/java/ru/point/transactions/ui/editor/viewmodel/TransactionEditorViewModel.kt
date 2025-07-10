package ru.point.transactions.ui.editor.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.transactions.domain.usecase.GetCategoriesByTypeUseCase
import ru.point.transactions.domain.usecase.GetTransactionUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.model.toAppError
import javax.inject.Inject

internal class TransactionEditorViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val transactionId: Int?,
    private val isIncome: Boolean,
) : MviViewModel<TransactionEditorState, TransactionEditorAction, Any>(initialState = TransactionEditorState()) {

    init {
        if (transactionId != null) {
            loadTransaction()
        }
        loadCategories()
    }

    override fun reduce(action: TransactionEditorAction, state: TransactionEditorState): TransactionEditorState {
        return when (action) {

            is TransactionEditorAction.TransactionLoadSuccess -> with(action.transactionVo) {
                state.copy(
                    accountName = accountName,
                    categoryName = categoryName,
                    amountValue = amount.toFormattedCurrency(currency),
                    currency = currency,
                    transactionDate = transactionDate,
                    transactionTime = transactionTime,
                    comment = comment,
                    isLoading = false,
                )
            }

            is TransactionEditorAction.TransactionLoadRequested -> state.copy(isLoading = true)

            is TransactionEditorAction.TransactionLoadError -> state.copy(isLoading = false, error = action.error)

            is TransactionEditorAction.OnCommentChanged -> state.copy(comment = action.comment)

            is TransactionEditorAction.OnAmountChange -> state.copy(
                amountValue = action.amount.toFormattedCurrency(
                    state.currency
                )
            )

            is TransactionEditorAction.OnDateChange -> state.copy(transactionDate = action.date)

            is TransactionEditorAction.OnTimeChange -> state.copy(transactionTime = action.time)

            is TransactionEditorAction.OnCategoryChange -> state.copy(categoryName = action.category)

            is TransactionEditorAction.CategoriesLoadError -> TODO()

            is TransactionEditorAction.CategoriesLoadRequested -> TODO()

            is TransactionEditorAction.CategoriesLoadSuccess -> state.copy(categoriesList = action.categories)
        }
    }

    private fun loadTransaction() {
        viewModelScope.launch {
            onAction(TransactionEditorAction.TransactionLoadRequested)
            getTransactionUseCase(transactionId!!).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.TransactionLoadSuccess(transactionVo = it))
                },
                onFailure = {
                    onAction(TransactionEditorAction.TransactionLoadError(it.toAppError()))
                }
            )
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {

            getCategoriesByTypeUseCase(isIncome = isIncome).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.CategoriesLoadSuccess(it))
                },
                onFailure = {
                    onAction(TransactionEditorAction.CategoriesLoadError(it.toAppError()))
                }
            )
        }
    }
}
