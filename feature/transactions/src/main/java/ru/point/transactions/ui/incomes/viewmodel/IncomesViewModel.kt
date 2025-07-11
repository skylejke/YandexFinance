package ru.point.transactions.ui.incomes.viewmodel

import ru.point.transactions.domain.usecase.GetIncomesUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject

/**
 * ViewModel, отвечающая за загрузку и управление состоянием экрана с доходами пользователя.
 *
 * Выполняет вызов [GetIncomesUseCase], обрабатывает результат и преобразует его в UI-состояние
 * в рамках паттерна MVI.
 */
internal class IncomesViewModel @Inject constructor(private val getIncomesUseCase: GetIncomesUseCase) :
    MviViewModel<IncomesState, IncomesAction, Any>(initialState = IncomesState()) {

    init {
        onHandleLoadIncomes()
    }

    override fun reduce(action: IncomesAction, state: IncomesState): IncomesState {
        return when (action) {
            is IncomesAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is IncomesAction.LoadSuccess -> state.copy(isLoading = false, incomes = action.incomes)
            is IncomesAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun onHandleLoadIncomes() {
        handleAction<IncomesAction.LoadRequested> {
            getIncomesUseCase().fold(
                onSuccess = { incomes ->
                    onAction(IncomesAction.LoadSuccess(incomes = incomes))
                },
                onFailure = { error ->
                    onAction(IncomesAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}