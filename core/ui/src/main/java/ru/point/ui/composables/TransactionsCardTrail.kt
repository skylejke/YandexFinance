package ru.point.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import ru.point.core.res.common.R
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.GhostGray
import ru.point.ui.colors.Graphite
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.extensions.toTimeHHmm

@Composable
fun TransactionsCardTrail(
    amount: String,
    currency: String,
    modifier: Modifier = Modifier,
    transactionDate: String? = null,
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
                text = amount.toFormattedCurrency(currency),
                style = MaterialTheme.typography.bodyLarge,
                color = Graphite
            )
            if (transactionDate != null) {
                Text(
                    text = transactionDate.toTimeHHmm(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = CharcoalGrey
                )
            }
        }
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.more_icon),
            contentDescription = null,
            tint = GhostGray
        )
    }
}