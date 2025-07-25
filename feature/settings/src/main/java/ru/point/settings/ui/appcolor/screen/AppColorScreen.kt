package ru.point.settings.ui.appcolor.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.NonRestartableComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.core.resources.R
import ru.point.settings.di.component.DaggerAppColorComponent
import ru.point.settings.di.deps.SettingsDepsProvider
import ru.point.settings.ui.appcolor.content.AppColorPicker
import ru.point.settings.ui.appcolor.viewmodel.AppColorViewModel
import ru.point.ui.scaffold.bottombar.BottomBarState
import ru.point.ui.scaffold.fab.FabState
import ru.point.ui.scaffold.topappbar.TopAppBarState

@NonRestartableComposable
@Composable
fun AppColorScreen(
    topAppBarState: MutableState<TopAppBarState>,
    fabState: MutableState<FabState>,
    bottomBarState: MutableState<BottomBarState>,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {

    topAppBarState.value = TopAppBarState(
        titleRes = R.string.primary_color,
        onBack = onBack
    )

    fabState.value = FabState.Hidden

    bottomBarState.value = BottomBarState.Hidden

    val appColorComponent = remember {
        DaggerAppColorComponent.builder().deps(settingsDeps = SettingsDepsProvider.settingsDeps)
            .build()
    }

    val viewModel = viewModel<AppColorViewModel>(factory = appColorComponent.appColorViewModelFactory)

    val state by viewModel.state.collectAsStateWithLifecycle()

    AppColorPicker(
        state = state,
        onColorSelected = viewModel::onAction,
        modifier = modifier,
    )
}