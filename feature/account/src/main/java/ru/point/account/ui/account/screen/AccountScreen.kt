package ru.point.account.ui.account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.account.R
import ru.point.account.ui.account.content.AccountScreenContent
import ru.point.account.ui.account.viewmodel.AccountAction
import ru.point.account.ui.account.viewmodel.AccountViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.di.LocalViewModelFactory
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
                icon = ImageVector.vectorResource(R.drawable.edit_icon),
                action = {
                    onNavigate()
                }
            )
        )
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Showed

    val viewModel = viewModel<AccountViewModel>(factory = LocalViewModelFactory.current)

    val state by viewModel.state.collectAsStateWithLifecycle()

    val isOnline by LocalInternetTracker.current.online.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onAction(AccountAction.LoadRequested)
    }

    if (!isOnline) {
        NoInternetBanner(modifier = modifier)
    } else {
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
}