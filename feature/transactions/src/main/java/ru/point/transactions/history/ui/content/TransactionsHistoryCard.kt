package ru.point.transactions.history.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import ru.point.ui.colors.Mint
import ru.point.ui.composables.BaseListItem
import ru.point.ui.composables.CategoryIcon
import ru.point.ui.composables.TransactionCardContent
import ru.point.ui.composables.TransactionsCardTrail
import ru.point.utils.extensions.initials

@Composable
internal fun TransactionsHistoryCard(
    title: String,
    subtitle: String?,
    emojiIcon: String?,
    amount: String,
    currency: String,
    transactionDate: String,
    modifier: Modifier = Modifier
){
    BaseListItem(
        content = {
            TransactionCardContent(title = title, subtitle = subtitle)
        },
        lead = {
            CategoryIcon(
                emojiIcon = emojiIcon ?: title.initials(),
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Mint)
            )
        },
        trail = {
            TransactionsCardTrail(
                amount = amount,
                currency = currency,
                transactionDate = transactionDate
            )
        },
        modifier = modifier
    )
}
