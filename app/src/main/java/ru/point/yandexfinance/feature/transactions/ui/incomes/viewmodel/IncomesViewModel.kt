package ru.point.yandexfinance.feature.transactions.ui.incomes.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.model.toUserMessage
import ru.point.yandexfinance.feature.transactions.model.toIncome
import ru.point.yandexfinance.network.RetrofitInstance
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class IncomesViewModel : ViewModel() {
    private val _state = MutableStateFlow(IncomesState())
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<IncomesAction>()
    val actions get() = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            _actions.collect { action ->
                _state.update { reduce(action, it) }
            }
        }

        loadIncomes()
    }

    fun onAction(action: IncomesAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun reduce(action: IncomesAction, state: IncomesState): IncomesState {
        return when (action) {
            is IncomesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is IncomesAction.LoadSuccess -> state.copy(isLoading = false, incomes = action.incomes)
            is IncomesAction.LoadError -> state.copy(isLoading = false, error = action.message)
        }
    }

    private fun loadIncomes() {
        viewModelScope.launch(Dispatchers.IO) {
            _actions.emit(IncomesAction.LoadRequested)

            RetrofitInstance.transactionsApi.getTransactionsForPeriod(
                accountId = 24,
                start = LocalDate.now().format(DateTimeFormatter.ISO_DATE),
                end = LocalDate.now().format(DateTimeFormatter.ISO_DATE)
            ).fold(
                onSuccess = { incomes ->
                    _actions.emit(
                        IncomesAction.LoadSuccess(
                            incomes = incomes
                                .filter { it.category.isIncome }
                                .map { it.toIncome })
                    )
                },
                onFailure = { error ->
                    _actions.emit(IncomesAction.LoadError(error.toAppError().toUserMessage()))
                }
            )
        }
    }
}