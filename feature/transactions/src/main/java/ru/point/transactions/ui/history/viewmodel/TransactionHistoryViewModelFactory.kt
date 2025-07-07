package ru.point.transactions.ui.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetTransactionsHistoryUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class TransactionHistoryViewModelFactory @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        TransactionHistoryViewModel(getTransactionsHistoryUseCase = getTransactionsHistoryUseCase) as T
}