package ru.point.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.Graphite

@Composable
fun TransactionCardContent(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Column(horizontalAlignment = Alignment.Start, modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            color = Graphite
        )
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = CharcoalGrey
            )
        }
    }
}