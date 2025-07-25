package ru.point.settings.ui.settings.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.core.resources.R
import ru.point.navigation.NavigationRoute
import ru.point.settings.di.component.DaggerSettingsComponent
import ru.point.settings.di.deps.SettingsDepsProvider
import ru.point.settings.ui.settings.content.SettingsScreenContent
import ru.point.settings.ui.settings.viewmodel.SettingsViewModel
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState

@NonRestartableComposable
@Composable
fun SettingsScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onNavigate: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.settings,
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Showed

    val settingsComponent = remember {
        DaggerSettingsComponent.builder().deps(settingsDeps = SettingsDepsProvider.settingsDeps).build()
    }

    val viewModel =
        viewModel<SettingsViewModel>(factory = settingsComponent.settingsViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreenContent(
        state = state,
        onAction = viewModel::onAction,
        onNavigate = onNavigate,
        modifier = modifier
    )
}