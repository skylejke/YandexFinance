package ru.point.transactions.ui.editor.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Lazy
import ru.point.transactions.domain.usecase.CreateTransactionUseCase
import ru.point.transactions.domain.usecase.DeleteTransactionUseCase
import ru.point.transactions.domain.usecase.GetAccountDataUseCase
import ru.point.transactions.domain.usecase.GetCategoriesByTypeUseCase
import ru.point.transactions.domain.usecase.GetTransactionUseCase
import ru.point.transactions.domain.usecase.UpdateTransactionUseCase
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
internal class TransactionEditorViewModelFactory @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val getAccountDataUseCase: GetAccountDataUseCase,
    private val createTransactionUseCase: Lazy<CreateTransactionUseCase>,
    private val updateTransactionUseCase: Lazy<UpdateTransactionUseCase>,
    private val deleteTransactionUseCase: Lazy<DeleteTransactionUseCase>,
    @Named("transactionId") private val transactionId: Int?,
    @Named("isIncome") private val isIncome: Boolean,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        TransactionEditorViewModel(
            getTransactionUseCase = getTransactionUseCase,
            getCategoriesByTypeUseCase = getCategoriesByTypeUseCase,
            getAccountDataUseCase = getAccountDataUseCase,
            createTransactionUseCase = createTransactionUseCase,
            updateTransactionUseCase = updateTransactionUseCase,
            deleteTransactionUseCase = deleteTransactionUseCase,
            transactionId = transactionId,
            isIncome = isIncome,
        ) as T
}