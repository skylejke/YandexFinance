package ru.point.account.ui.account.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.account.ui.account.content.AccountScreenContent
import ru.point.account.ui.account.viewmodel.AccountAction
import ru.point.account.ui.account.viewmodel.AccountViewModel
import ru.point.ui.composables.ErrorContent
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.NoInternetBanner
import ru.point.ui.di.LocalInternetTracker
import ru.point.ui.di.LocalViewModelFactory
import ru.point.utils.model.toUserMessage

/**
 * Экран отображения информации об аккаунте пользователя.
 *
 * Подключает [AccountViewModel], отслеживает состояние загрузки, ошибок и подключения к интернету,
 * и отображает соответствующий UI: данные аккаунта, индикатор загрузки, сообщение об ошибке или баннер отсутствия интернета.
 */
@Composable
fun AccountScreen(modifier: Modifier = Modifier) {

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