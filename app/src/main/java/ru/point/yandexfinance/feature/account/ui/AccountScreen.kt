package ru.point.yandexfinance.feature.account.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.extensions.toCurrencySymbol
import ru.point.yandexfinance.core.common.extensions.toFormattedCurrency
import ru.point.yandexfinance.core.common.ui.composables.BaseListItem
import ru.point.yandexfinance.core.common.ui.composables.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.composables.NoInternetBanner
import ru.point.yandexfinance.core.common.utils.InternetHolder
import ru.point.yandexfinance.feature.account.ui.viewmodel.AccountAction
import ru.point.yandexfinance.feature.account.ui.viewmodel.AccountState
import ru.point.yandexfinance.feature.account.ui.viewmodel.AccountViewModel
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint


@Composable
fun AccountScreen(modifier: Modifier = Modifier) {

    val viewModel = viewModel<AccountViewModel>()

    val state by viewModel.state.collectAsState()

    val tracker = remember { InternetHolder.tracker }

    val isOnline by tracker.online.collectAsState()

    if (!isOnline) {
        NoInternetBanner(
            modifier = Modifier.fillMaxSize(),
            internetTracker = tracker
        )
        return
    }

    when {
        state.isLoading -> {
            Text("Загрузка...", modifier = Modifier.padding(16.dp))
        }

        state.error != null -> {
            Text(
                text = state.error!!,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        state.account != null -> {
            AccountScreenList(
                state = state,
                onAction = viewModel::onAction,
                modifier = modifier
            )
        }
    }
}

@Composable
private fun AccountScreenList(
    state: AccountState,
    onAction: (AccountAction) -> Unit,
    modifier: Modifier = Modifier
) {

    val accountCardModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(Mint)

    LazyColumn(modifier = modifier) {
        item(key = state.account?.id) {

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
                    AccountCardTrailingElement(
                        trailingText = state.account?.balance.toString()
                            .toFormattedCurrency(state.account?.currency.toString().toCurrencySymbol())
                    )
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
                    AccountCardTrailingElement(trailingText = state.account?.currency.toString().toCurrencySymbol())
                }
            )
        }
    }
}

@Composable
private fun AccountCardTrailingElement(trailingText: String, modifier: Modifier = Modifier) {
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