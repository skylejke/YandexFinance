package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.settings.ui.content.SettingsScreen
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.navigation.Route

fun NavGraphBuilder.settingsFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
) {
    composable<Route.Settings> {
        SettingsScreen(
            topAppBarState = topAppBarState,
            fabState = fabState,
            bottomBarState = bottomBarState,
            modifier = Modifier.fillMaxSize()
        )
    }
}