package ru.point.ui.composables

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ru.point.ui.R

@Composable
fun TotalToday(sum: String, modifier: Modifier = Modifier) {
    BaseListItem(
        content = {
            Text(
                text = stringResource(R.string.total),
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trail = {
            Text(
                text = sum
            )
        },
        modifier = modifier
    )
}