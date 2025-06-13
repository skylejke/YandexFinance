package ru.point.yandexfinance.core.common.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ru.point.yandexfinance.R

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

@Preview(showBackground = true)
@Composable
private fun ExpensesTodayPreview() {
    TotalToday("436 558 ₽")
}