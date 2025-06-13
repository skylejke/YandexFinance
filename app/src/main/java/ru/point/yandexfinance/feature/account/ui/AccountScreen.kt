package ru.point.yandexfinance.feature.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.ui.BaseListItem
import ru.point.yandexfinance.core.common.ui.GreyHorizontalDivider
import ru.point.yandexfinance.feature.account.model.AccountBrief
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint

val accountBrief = AccountBrief(id = 1, balance = "-670 000 ₽", currency = "₽")

@Composable
fun AccountScreen(modifier: Modifier = Modifier) {
    AccountScreenList(modifier = modifier)
}

@Composable
internal fun AccountScreenList(modifier: Modifier = Modifier) {

    val accountCardModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(Mint)

    LazyColumn(modifier = modifier) {
        item(key = accountBrief.id) {

            BaseListItem(
                modifier = accountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp),
                content = {
                    Text(
                        text = stringResource(R.string.balance),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Graphite
                    )
                },
                lead = {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "💰",
                            style = MaterialTheme.typography.bodyLarge,
                            color = Graphite
                        )
                    }
                },
                trail = {
                    AccountCardTrailingElement(trailingText = accountBrief.balance)
                }
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

            BaseListItem(
                modifier = accountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp),
                content = {
                    Text(
                        text = stringResource(R.string.currency),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Graphite
                    )
                },
                trail = {
                    AccountCardTrailingElement(trailingText = accountBrief.currency)
                }
            )
        }
    }
}

@Composable
internal fun AccountCardTrailingElement(trailingText: String, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Text(
            text = trailingText,
            style = MaterialTheme.typography.bodyLarge,
            color = Graphite
        )
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.more_icon),
            contentDescription = null,
            tint = GhostGray
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AccountScreenListPreview() {
    AccountScreenList()
}