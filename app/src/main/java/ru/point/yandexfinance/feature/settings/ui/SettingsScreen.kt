package ru.point.yandexfinance.feature.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.ui.BaseListItem
import ru.point.yandexfinance.core.common.ui.GreyHorizontalDivider
import ru.point.yandexfinance.feature.settings.model.Setting
import ru.point.yandexfinance.ui.theme.MintGreen

private val settingsTitles = listOf(
    Setting(R.string.setting_color),
    Setting(R.string.setting_sound),
    Setting(R.string.setting_haptics),
    Setting(R.string.setting_passcode),
    Setting(R.string.setting_sync),
    Setting(R.string.setting_language),
    Setting(R.string.setting_about),
)

@Composable
fun SettingsScreen(modifier: Modifier = Modifier) {
    SettingsList(modifier = modifier)
}

@Composable
internal fun SettingsList(modifier: Modifier = Modifier) {
    var isDarkTheme by remember { mutableStateOf(false) }

    val settingsCardModifier = Modifier
        .fillMaxWidth()
        .height(56.dp)

    LazyColumn(modifier = modifier) {
        item(key = R.string.setting_theme) {
            BaseListItem(
                modifier = settingsCardModifier.padding(horizontal = 16.dp),
                content = {
                    Text(
                        text = stringResource(R.string.setting_theme),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                trail = {
                    Switch(
                        checked = isDarkTheme,
                        onCheckedChange = { isDarkTheme = it },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MintGreen
                        )
                    )
                }
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        items(items = settingsTitles, key = { it.settingTitleResId }) {
            BaseListItem(
                modifier = Modifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp),
                content = {
                    Text(
                        text = stringResource(it.settingTitleResId),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                trail = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.trailing_icon),
                        contentDescription = null
                    )
                }
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}