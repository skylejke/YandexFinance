package ru.point.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

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
        )
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}