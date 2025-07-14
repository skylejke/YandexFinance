package ru.point.transactions.ui.analysis.viewmodel

import ru.point.transactions.domain.model.AnalysisTransaction
import ru.point.utils.model.AppError

internal sealed interface AnalysisAction {

    data object InitialLoadRequested : AnalysisAction

    data class LoadRequested(val state: AnalysisState) : AnalysisAction

    data class LoadError(val error: AppError) : AnalysisAction

    data class LoadSuccess(val analysisTransactions: List<AnalysisTransaction>) : AnalysisAction

    data class OnStartMonthChanged(val startMonth: String) : AnalysisAction

    data class OnEndMonthChanged(val endMonth: String) : AnalysisAction
}