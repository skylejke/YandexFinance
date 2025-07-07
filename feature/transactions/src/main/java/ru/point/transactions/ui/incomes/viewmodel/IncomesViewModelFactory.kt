package ru.point.transactions.ui.incomes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetIncomesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class IncomesViewModelFactory @Inject constructor(
    private val getIncomesUseCase: GetIncomesUseCase
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        IncomesViewModel(getIncomesUseCase = getIncomesUseCase) as T
}