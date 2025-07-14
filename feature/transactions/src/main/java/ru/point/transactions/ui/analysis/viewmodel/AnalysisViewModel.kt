package ru.point.transactions.ui.analysis.viewmodel

import ru.point.transactions.domain.usecase.GetAnalysisTransactionsUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.extensions.dateFormatter
import ru.point.utils.extensions.toIsoDate
import ru.point.utils.model.toAppError
import java.time.LocalDate
import javax.inject.Inject

internal class AnalysisViewModel @Inject constructor(
    private val getAnalysisTransactionsUseCase: GetAnalysisTransactionsUseCase,
    private val isIncome: Boolean,
) : MviViewModel<AnalysisState, AnalysisAction, Any>(
    initialState = AnalysisState(
        startDate = LocalDate.now().withDayOfMonth(1).format(dateFormatter),
        endDate = LocalDate.now().format(dateFormatter)
    )
) {

    init {
        onHandleInitialLoadRequested()
        onHandleOnStartMonthChanged()
        onHandleOnEndMonthChanged()
        onHandleLoadRequested()
    }

    override fun reduce(action: AnalysisAction, state: AnalysisState): AnalysisState {
        return when (action) {

            is AnalysisAction.InitialLoadRequested -> state.copy(isLoading = true, error = null)

            is AnalysisAction.LoadSuccess -> state.copy(
                isLoading = false,
                transactions = action.analysisTransactions
            )

            is AnalysisAction.LoadError -> state.copy(isLoading = false, error = action.error)

            is AnalysisAction.OnEndMonthChanged -> state.copy(endDate = action.endMonth)

            is AnalysisAction.OnStartMonthChanged -> state.copy(startDate = action.startMonth)

            is AnalysisAction.LoadRequested -> state
        }
    }

    private fun onHandleLoadRequested(){
        handleAction<AnalysisAction.LoadRequested> { action ->
            val localState = action.state
            getAnalysisTransactionsUseCase(
                isIncome = isIncome,
                startDate = localState.startDate.toIsoDate(),
                endDate = localState.endDate.toIsoDate(),
            ).fold(
                onSuccess = { analysisTransaction ->
                    onAction(AnalysisAction.LoadSuccess(analysisTransactions = analysisTransaction))
                },
                onFailure = { error ->
                    onAction(AnalysisAction.LoadError(error.toAppError()))
                }
            )
        }
    }

    private fun onHandleOnEndMonthChanged(){
        handleAction<AnalysisAction.OnEndMonthChanged> {
            onAction(AnalysisAction.LoadRequested(state = state.value.copy(endDate = it.endMonth)))
        }
    }

    private fun onHandleOnStartMonthChanged(){
        handleAction<AnalysisAction.OnStartMonthChanged> {
            onAction(AnalysisAction.LoadRequested(state = state.value.copy(startDate = it.startMonth)))
        }
    }

    private fun onHandleInitialLoadRequested() {
        handleAction<AnalysisAction.InitialLoadRequested> {
            onAction(AnalysisAction.LoadRequested(state = state.value))
        }
    }
}