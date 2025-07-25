package ru.point.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import ru.point.utils.model.PrimaryColor

@Composable
fun YandexFinanceTheme(
    primaryColor: PrimaryColor,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when (primaryColor) {
        PrimaryColor.Purple ->
            if (darkTheme) AppColorScheme.PurpleColorScheme.purpleDark
            else AppColorScheme.PurpleColorScheme.purpleLight

        PrimaryColor.Blue ->
            if (darkTheme) AppColorScheme.BlueColorScheme.blueDark
            else AppColorScheme.BlueColorScheme.blueLight

        PrimaryColor.Red ->
            if (darkTheme) AppColorScheme.RedColorScheme.redDark
            else AppColorScheme.RedColorScheme.redLight

        PrimaryColor.Yellow ->
            if (darkTheme) AppColorScheme.YellowColorScheme.yellowDark
            else AppColorScheme.YellowColorScheme.yellowLight

        PrimaryColor.Green ->
            if (darkTheme) AppColorScheme.GreenColorScheme.greenDark
            else AppColorScheme.GreenColorScheme.greenLight
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}