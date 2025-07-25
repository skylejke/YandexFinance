package ru.point.transactions.ui.analysis.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.core.resources.R
import ru.point.transactions.di.component.DaggerAnalysisComponent
import ru.point.transactions.di.deps.TransactionDepsStore
import ru.point.transactions.ui.analysis.content.AnalysisScreenContent
import ru.point.transactions.ui.analysis.viewmodel.AnalysisAction
import ru.point.transactions.ui.analysis.viewmodel.AnalysisViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

@NonRestartableComposable
@Composable
fun AnalysisScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    isIncome: Boolean,
    modifier: Modifier = Modifier,
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.analysis,
        onBack = onBack
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    val analysisComponent = remember {
        DaggerAnalysisComponent
            .builder()
            .deps(transactionDeps = TransactionDepsStore.transactionDeps)
            .isIncome(isIncome)
            .build()
    }

    val viewModel = viewModel<AnalysisViewModel>(factory = analysisComponent.analysisViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(AnalysisAction.InitialLoadRequested)
    }

    RequiredInternetContent(
        modifier = modifier,
        content = {
            when {

                state.isLoading -> {
                    LoadingIndicator(modifier = modifier)
                }

                state.error != null -> {
                    ErrorContent(
                        message = state.error!!.toUserMessage(),
                        modifier = modifier
                    )
                }

                else -> {
                    AnalysisScreenContent(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = modifier
                    )
                }
            }
        }
    )
}