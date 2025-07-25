package ru.point.settings.ui.settings.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.navigation.NavigationRoute
import ru.point.settings.ui.settings.viewmodel.SettingsAction
import ru.point.settings.ui.settings.viewmodel.SettingsState
import ru.point.ui.composables.GreyHorizontalDivider

@Composable
internal fun SettingsScreenContent(
    state: SettingsState,
    onAction: (SettingsAction) -> Unit,
    onNavigate: (NavigationRoute) -> Unit,
    modifier: Modifier = Modifier
) {

    val settingsCardModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)

    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {

        SwitchThemeCard(
            modifier = settingsCardModifier.padding(horizontal = 16.dp),
            isDarkTheme = state.isDarkThemeEnabled,
            switchTheme = { onAction(SettingsAction.OnToggleDarkTheme(it)) }
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {
                        onNavigate(NavigationRoute.SettingsFeature.AppColor)
                    }
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_color
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {}
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_sound
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {}
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_haptics
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {}
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_passcode
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {}
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_sync
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {}
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_language
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SettingCard(
            modifier = settingsCardModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {
                        onNavigate(NavigationRoute.SettingsFeature.AppInfo)
                    }
                )
                .padding(horizontal = 16.dp),
            settingTitleResId = R.string.setting_about
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}