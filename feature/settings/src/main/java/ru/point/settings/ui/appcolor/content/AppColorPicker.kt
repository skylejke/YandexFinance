package ru.point.settings.ui.appcolor.content

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.settings.ui.appcolor.viewmodel.AppColorAction
import ru.point.settings.ui.appcolor.viewmodel.AppColorState
import ru.point.ui.theme.AppColorScheme
import ru.point.utils.model.PrimaryColor

@Composable
internal fun AppColorPicker(
    state: AppColorState,
    onColorSelected: (AppColorAction) -> Unit,
    modifier: Modifier = Modifier
) {
    val colorOptions = listOf(
        PrimaryColor.Green to AppColorScheme.GreenColorScheme.greenLight.primary,
        PrimaryColor.Purple to AppColorScheme.PurpleColorScheme.purpleLight.primary,
        PrimaryColor.Blue to AppColorScheme.BlueColorScheme.blueLight.primary,
        PrimaryColor.Red to AppColorScheme.RedColorScheme.redLight.primary,
        PrimaryColor.Yellow to AppColorScheme.YellowColorScheme.yellowLight.primary,
    )

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.pick_primary_color),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(16.dp)
        )

        colorOptions.forEach { (colorType, displayColor) ->
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
                    .background(displayColor)
                    .border(
                        width = if (state.selectedColor == colorType) 4.dp else 0.dp,
                        color = if (state.selectedColor == colorType) MaterialTheme.colorScheme.inverseSurface else Color.Transparent,
                        shape = CircleShape
                    )
                    .clickable { onColorSelected(AppColorAction.OnTogglePrimaryColor(colorType)) }
            )
        }
    }
}
