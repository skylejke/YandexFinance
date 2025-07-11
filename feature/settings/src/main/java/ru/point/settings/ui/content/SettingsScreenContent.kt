package ru.point.settings.ui.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.core.res.settings.R
import ru.point.settings.domain.model.Setting
import ru.point.ui.composables.GreyHorizontalDivider

@Composable
internal fun SettingsScreenContent(
    modifier: Modifier = Modifier
) {
    var isDarkTheme by remember { mutableStateOf(false) }

    val settingsCardModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)

    LazyColumn(modifier = modifier) {
        item(key = R.string.setting_theme) {
            SwitchThemeCard(
                modifier = settingsCardModifier.padding(horizontal = 16.dp),
                isDarkTheme = isDarkTheme,
                switchTheme = { isDarkTheme = it }
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = settingsTitles, key = { it.settingTitleResId }) {
            SettingCard(
                modifier = settingsCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp),
                settingTitleResId = it.settingTitleResId
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}

private val settingsTitles = listOf(
    Setting(R.string.setting_color),
    Setting(R.string.setting_sound),
    Setting(R.string.setting_haptics),
    Setting(R.string.setting_passcode),
    Setting(R.string.setting_sync),
    Setting(R.string.setting_language),
    Setting(R.string.setting_about),
)
