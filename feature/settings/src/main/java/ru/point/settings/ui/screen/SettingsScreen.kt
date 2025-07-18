package ru.point.settings.ui.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.ui.Modifier
import ru.point.core.res.settings.R
import ru.point.settings.ui.content.SettingsScreenContent
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState

/**
 * Экран настроек приложения.
 *
 * Отображает UI с настройками пользователя.
 */
@NonRestartableComposable
@Composable
fun SettingsScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    modifier: Modifier = Modifier
) {


    topAppBarState.value = TopAppBarState(
        titleRes = R.string.settings,
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Showed

    SettingsScreenContent(modifier = modifier)
}