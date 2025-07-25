package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.settings.ui.appcolor.screen.AppColorScreen
import ru.point.settings.ui.appinfo.screen.AppInfoScreen
import ru.point.settings.ui.settings.screen.SettingsScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.ComposeNavigationRoute
import ru.point.yandexfinance.navigation.asComposeNavigationRoute

fun NavGraphBuilder.settingsFeature(
    navController: NavController,
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<ComposeNavigationRoute.SettingsFeature.Settings> {
        SettingsScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onNavigate = { navigationRoute -> navController.navigate(navigationRoute.asComposeNavigationRoute) },
            modifier = Modifier.fillMaxSize()
        )
    }

    composable<ComposeNavigationRoute.SettingsFeature.AppInfo> {
        AppInfoScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::popBackStack,
            modifier = Modifier.fillMaxSize(),
        )
    }

    composable<ComposeNavigationRoute.SettingsFeature.AppColor> {
        AppColorScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            onBack = navController::popBackStack,
            modifier = Modifier.fillMaxSize(),
        )
    }
}