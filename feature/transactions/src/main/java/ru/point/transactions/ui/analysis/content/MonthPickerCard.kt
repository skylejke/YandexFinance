package ru.point.transactions.ui.analysis.content

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.ui.composables.BaseListItem

@Composable
internal fun MonthPickerCard(
    @StringRes contentTextResId: Int,
    month: String,
    modifier: Modifier = Modifier,
) {

    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = stringResource(contentTextResId)
            )
        },
        trail = {
            Box(
                modifier = Modifier
                    .height(36.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(100.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = month,
                    maxLines = 1,
                    style = MaterialTheme.typography.titleMedium,
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun MonthPickerCardPreview() {
    MonthPickerCard(
        contentTextResId = R.string.period_start,
        month = "февраль 2025",
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Composable
private fun MonthPickerCardPreview1() {
    MonthPickerCard(
        contentTextResId = R.string.period_end,
        month = "март 2025",
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}