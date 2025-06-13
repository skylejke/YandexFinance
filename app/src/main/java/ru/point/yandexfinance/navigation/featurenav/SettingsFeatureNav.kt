package ru.point.yandexfinance.navigation.featurenav

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import ru.point.yandexfinance.R
import ru.point.yandexfinance.navigation.Route
import ru.point.yandexfinance.core.common.ui.scaffold.fab.FabState
import ru.point.yandexfinance.core.common.ui.scaffold.topappbar.TopAppBarState
import ru.point.yandexfinance.feature.settings.ui.SettingsScreen

fun NavGraphBuilder.settingsFeature(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>
) {
    composable<Route.Settings> {
        topAppBarState.value = TopAppBarState(
            titleRes = R.string.settings,
        )

        fabState.value = FabState.Hidden

        SettingsScreen(modifier = Modifier.fillMaxSize())
    }
}