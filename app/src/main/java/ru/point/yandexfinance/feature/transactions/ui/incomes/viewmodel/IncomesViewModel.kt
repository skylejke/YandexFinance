package ru.point.yandexfinance.feature.transactions.ui.incomes.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.ui.MviViewModel
import ru.point.yandexfinance.feature.transactions.model.toIncome
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomesViewModel : MviViewModel<IncomesState, IncomesAction, Any>(initialState = IncomesState()) {

    init {
        loadIncomes()
    }

    override fun reduce(action: IncomesAction, state: IncomesState): IncomesState {
        return when (action) {
            is IncomesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is IncomesAction.LoadSuccess -> state.copy(isLoading = false, incomes = action.incomes)
            is IncomesAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun loadIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            onAction(IncomesAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                start = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { incomes ->
                    onAction(
                        IncomesAction.LoadSuccess(
                            incomes = incomes
                                .filter { it.category.isIncome }
                                .map { it.toIncome })
                    )
                },
                onFailure = { error ->
                    onAction(IncomesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}