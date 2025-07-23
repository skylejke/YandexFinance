package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.account.ui.account.screen.AccountScreen
import ru.point.account.ui.update.screen.UpdateAccountScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.ComposeNavigationRoute
import ru.point.yandexfinance.navigation.asComposeNavigationRoute

fun NavGraphBuilder.accountFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<ComposeNavigationRoute.AccountFeature.Account> {
        AccountScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navigationRoute -> navController.navigate(navigationRoute.asComposeNavigationRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.AccountFeature.EditAccount> {
        UpdateAccountScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::popBackStack,
            modifier = Modifier.fillMaxSize()
        )
    }
}