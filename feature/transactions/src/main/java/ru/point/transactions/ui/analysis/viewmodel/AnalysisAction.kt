package ru.point.transactions.ui.analysis.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.utils.model.AppError
import ru.point.vo.AnalysisCategory

@Immutable
internal sealed interface AnalysisAction {

    data object InitialLoadRequested : AnalysisAction

    data class LoadRequested(val state: AnalysisState) : AnalysisAction

    data class LoadError(val error: AppError) : AnalysisAction

    data class LoadSuccess(val analysisCategories: List<AnalysisCategory>) : AnalysisAction

    data class OnStartMonthChanged(val startMonth: String) : AnalysisAction

    data class OnEndMonthChanged(val endMonth: String) : AnalysisAction
}