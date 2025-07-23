package ru.point.account.ui.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.account.domain.usecase.CalculateTransactionDiffUseCase
import ru.point.account.domain.usecase.GetAccountUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class AccountViewModelFactory @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val calculateTransactionDiffUseCase: CalculateTransactionDiffUseCase,
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AccountViewModel(
            getAccountUseCase = getAccountUseCase,
            calculateTransactionDiffUseCase = calculateTransactionDiffUseCase,
        ) as T
}