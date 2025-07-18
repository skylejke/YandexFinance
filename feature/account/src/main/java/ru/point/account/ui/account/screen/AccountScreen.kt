package ru.point.account.ui.account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.account.di.component.DaggerAccountComponent
import ru.point.account.di.deps.AccountDepsProvider
import ru.point.account.ui.account.content.AccountScreenContent
import ru.point.account.ui.account.viewmodel.AccountAction
import ru.point.account.ui.account.viewmodel.AccountViewModel
import ru.point.core.res.account.R
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.RequiredInternetContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.utils.model.toUserMessage

/**
 * Экран отображения информации об аккаунте пользователя.
 *
 * Подключает [AccountViewModel], отслеживает состояние загрузки, ошибок и подключения к интернету,
 * и отображает соответствующий UI: данные аккаунта, индикатор загрузки, сообщение об ошибке или баннер отсутствия интернета.
 */
@NonRestartableComposable
@Composable
fun AccountScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.my_account,
        actions = listOf(
            TopAppBarAction(
                iconResId = R.drawable.edit_icon,
                action = onNavigate
            )
        )
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Showed

    val accountComponent = remember {
        DaggerAccountComponent.builder().deps(accountDeps = AccountDepsProvider.accountDeps).build()
    }

    val viewModel = viewModel<AccountViewModel>(factory = accountComponent.accountViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onAction(AccountAction.LoadRequested)
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

                state.account != null -> {
                    AccountScreenContent(
                        state = state,
                        modifier = modifier
                    )
                }
            }
        }
    )
}