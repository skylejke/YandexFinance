package ru.point.account.ui.update.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.account.di.component.DaggerUpdateAccountComponent
import ru.point.account.di.deps.AccountDepsProvider
import ru.point.account.ui.update.content.UpdateAccountScreenContent
import ru.point.account.ui.update.viewmodel.UpdateAccountAction
import ru.point.account.ui.update.viewmodel.UpdateAccountEvent
import ru.point.account.ui.update.viewmodel.UpdateAccountViewModel
import ru.point.core.res.account.R
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.extensions.showToast
import ru.point.utils.model.toUserMessage

@NonRestartableComposable
@Composable
fun UpdateAccountScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {

    val updateAccountComponent = remember {
        DaggerUpdateAccountComponent.builder().deps(accountDeps = AccountDepsProvider.accountDeps).build()
    }

    val viewModel = viewModel<UpdateAccountViewModel>(factory = updateAccountComponent.updateAccountViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val context = LocalContext.current

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.edit_account,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.check_icon,
                action = {
                    viewModel.onAction(UpdateAccountAction.OnUpdatePressed)
                }
            )
        ),
        onBack = onBack
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                UpdateAccountEvent.ShowSuccessToastAndGoBack -> {
                    showToast(context = context, messageResId = R.string.successfully_update_account)
                    onBack()
                }

                UpdateAccountEvent.ShowErrorToast -> {
                    showToast(context = context, messageResId = R.string.something_went_wrong)
                }
            }
        }
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
                    UpdateAccountScreenContent(
                        state = state,
                        onAction = viewModel::onAction,
                        modifier = modifier
                    )
                }
            }
        }
    )
}