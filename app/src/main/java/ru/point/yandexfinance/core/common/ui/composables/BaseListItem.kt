package ru.point.yandexfinance.core.common.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BaseListItem(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    lead: (@Composable () -> Unit)? = null,
    trail: (@Composable () -> Unit)? = null
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        if (lead != null) {
            lead()
        }

        Box(
            modifier = Modifier
                .weight(1f),
            contentAlignment = Alignment.CenterStart
        ) {
            content()
        }

        if (trail != null) {
            trail()
        }
    }
}
