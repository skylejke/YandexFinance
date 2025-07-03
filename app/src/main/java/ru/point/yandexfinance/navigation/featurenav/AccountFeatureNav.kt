package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.account.ui.account.screen.AccountScreen
import ru.point.account.ui.update.screen.UpdateAccountScreen
import ru.point.account.ui.update.viewmodel.UpdateAccountAction
import ru.point.account.ui.update.viewmodel.UpdateAccountViewModel
import ru.point.ui.di.LocalViewModelFactory
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarAction
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route

fun NavGraphBuilder.accountFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>
) {
    composable<Route.Account> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.my_account,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.edit_icon),
                    action = {
                        navController.navigate(Route.EditAccount)
                    }
                )
            )
        )

        fabState.value = FabState.Hidden

        AccountScreen(modifier = Modifier.fillMaxSize())
    }

    composable<Route.EditAccount> {
        val viewModel = viewModel<UpdateAccountViewModel>(factory = LocalViewModelFactory.current)

        val state by viewModel.state.collectAsStateWithLifecycle()

        topAppBarState.value = TopAppBarState(
            titleRes = R.string.edit_account,
            actions = listOf(
                TopAppBarAction(
                    icon = ImageVector.vectorResource(R.drawable.check_icon),
                    action = {
                        viewModel.onAction(UpdateAccountAction.OnUpdatePressed)
                    }
                )
            ),
            onBack = {
                navController.popBackStack()
            }
        )

        fabState.value = FabState.Hidden

        UpdateAccountScreen(
            state = state,
            onAction = viewModel::onAction,
            events = viewModel.events,
            onGoBackIfSuccess = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}