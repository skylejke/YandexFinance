package ru.point.account.ui.account.content

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.core.resources.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun AccountBalance(
    balance: String,
    modifier: Modifier = Modifier
) {
    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(R.string.balance),
                style = MaterialTheme.typography.bodyLarge,
            )
        },
        lead = {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "💰",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        },
        trail = {
            Text(
                text = balance,
                style = MaterialTheme.typography.bodyLarge,
            )
        }
    )
}