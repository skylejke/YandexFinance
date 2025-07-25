package ru.point.transactions.ui.history.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetTransactionsHistoryUseCase
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
internal class TransactionHistoryViewModelFactory @Inject constructor(
    private val getTransactionsHistoryUseCase: GetTransactionsHistoryUseCase,
    @param:Named("isIncome") private val isIncome: Boolean
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        TransactionHistoryViewModel(
            getTransactionsHistoryUseCase = getTransactionsHistoryUseCase,
            isIncome = isIncome
        ) as T
}