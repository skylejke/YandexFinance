package ru.point.transactions.ui.expenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetExpensesUseCase
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
internal class ExpensesViewModelFactory @Inject constructor(private val getExpensesUseCase: GetExpensesUseCase) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        ExpensesViewModel(getExpensesUseCase = getExpensesUseCase) as T
}