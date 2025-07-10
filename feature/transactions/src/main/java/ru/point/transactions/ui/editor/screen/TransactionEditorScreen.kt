package ru.point.transactions.ui.editor.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.transactions.R
import ru.point.transactions.di.component.DaggerTransactionEditorComponent
import ru.point.transactions.di.deps.TransactionDepsStore
import ru.point.transactions.ui.editor.content.TransactionEditorScreenContent
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

@NonRestartableComposable
@Composable
fun TransactionEditorScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: () -> Unit,
    isIncome: Boolean,
    transactionId: Int?,
    modifier: Modifier = Modifier,
) {

    topAppBarState.value = TopAppBarState(
        titleRes = if (isIncome) R.string.my_incomes else R.string.my_expenses,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.check_icon,
                action = onNavigate
            )
        ),
        onBack = onNavigate
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    val transactionEditorComponent = remember {
        DaggerTransactionEditorComponent
            .builder()
            .deps(transactionDeps = TransactionDepsStore.transactionDeps)
            .transactionId(transactionId = transactionId)
            .isIncome(isIncome = isIncome)
            .build()
    }

    val viewModel =
        viewModel<TransactionEditorViewModel>(factory = transactionEditorComponent.transactionEditorViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    when {
        isOnline.not() -> NoInternetBanner(modifier = modifier)

        state.isLoading -> LoadingIndicator(modifier = modifier)

        state.error != null -> ErrorContent(
            message = state.error!!.toUserMessage(),
            modifier = modifier
        )

        else -> TransactionEditorScreenContent(
            state = state,
            onAction = viewModel::onAction,
            modifier = modifier
        )
    }
}