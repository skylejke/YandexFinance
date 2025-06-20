package ru.point.yandexfinance.core.common.ui.composables

import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.ui.theme.LavenderGray

@Composable
fun GreyHorizontalDivider(modifier: Modifier = Modifier) {
    HorizontalDivider(
        color = LavenderGray,
        thickness = 1.dp,
        modifier = modifier
    )
}