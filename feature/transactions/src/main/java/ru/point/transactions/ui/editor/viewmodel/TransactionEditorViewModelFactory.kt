package ru.point.transactions.ui.editor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetCategoriesByTypeUseCase
import ru.point.transactions.domain.usecase.GetTransactionUseCase
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
internal class TransactionEditorViewModelFactory @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    @Named("transactionId") private val transactionId: Int?,
    @Named("isIncome") private val isIncome: Boolean,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        TransactionEditorViewModel(
            getTransactionUseCase = getTransactionUseCase,
            getCategoriesByTypeUseCase = getCategoriesByTypeUseCase,
            transactionId = transactionId,
            isIncome = isIncome,
        ) as T
}