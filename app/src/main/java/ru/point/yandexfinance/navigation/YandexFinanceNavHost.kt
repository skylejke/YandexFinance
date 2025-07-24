package ru.point.yandexfinance.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.featurenav.accountFeature
import ru.point.yandexfinance.navigation.featurenav.categoriesFeature
import ru.point.yandexfinance.navigation.featurenav.settingsFeature
import ru.point.yandexfinance.navigation.featurenav.transactionsFeature

@Composable
fun YandexFinanceNavHost(
    navHostController: NavHostController,
    startDestination: ComposeNavigationRoute,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        transactionsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
        )

        accountFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
        )

        categoriesFeature(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
        )

        settingsFeature(
            navController = navHostController,
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
        )
    }
}