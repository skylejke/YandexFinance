package ru.point.transactions.ui.analysis.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.point.core.resources.R
import ru.point.ui.colors.GhostGray
import ru.point.ui.colors.Graphite
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.CategoryIcon
import ru.point.utils.extensions.toFormattedCurrency

@Composable
internal fun AnalysisTransactionCard(
    emoji: String,
    title: String,
    amount: String,
    currency: String,
    part: String,
    modifier: Modifier = Modifier,
) {

    BaseListItem(
        modifier = modifier,
        content = {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite,
            )
        },
        lead = {
            CategoryIcon(
                emojiIcon = emoji,
            )
        },
        trail = {
            AnalysisTransactionCardTrail(
                part = part,
                amount = amount,
                currency = currency,
            )
        }
    )
}

@Composable
private fun AnalysisTransactionCardTrail(
    part: String,
    amount: String,
    currency: String,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = part,
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
            Text(
                text = amount.toFormattedCurrency(currency),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.more_icon),
            contentDescription = null,
            tint = GhostGray
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AnalysisTransactionCardPreview() {
    AnalysisTransactionCard(
        emoji = "💩",
        title = "Категория",
        amount = "100000",
        currency = "$",
        part = "30 %",
        modifier = Modifier
            .height(70.dp)
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    )
}