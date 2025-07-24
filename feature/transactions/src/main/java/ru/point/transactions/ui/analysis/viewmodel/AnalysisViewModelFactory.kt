package ru.point.transactions.ui.analysis.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.point.transactions.domain.usecase.GetAnalysisTransactionsUseCase
import javax.inject.Inject
import javax.inject.Named

@Suppress("UNCHECKED_CAST")
internal class AnalysisViewModelFactory @Inject constructor(
    private val getAnalysisTransactionsUseCase: GetAnalysisTransactionsUseCase,
    @param:Named("isIncome") private val isIncome: Boolean,
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AnalysisViewModel(
            getAnalysisTransactionsUseCase = getAnalysisTransactionsUseCase,
            isIncome = isIncome,
        ) as T
}