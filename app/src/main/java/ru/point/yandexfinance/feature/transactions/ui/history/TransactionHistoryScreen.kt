package ru.point.yandexfinance.feature.transactions.ui.history

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.point.yandexfinance.R
import ru.point.yandexfinance.core.common.extensions.toAmountInt
import ru.point.yandexfinance.core.common.extensions.toFormattedCurrency
import ru.point.yandexfinance.core.common.extensions.toTimeHHmm
import ru.point.yandexfinance.core.common.model.toUserMessage
import ru.point.yandexfinance.core.common.ui.composables.BaseListItem
import ru.point.yandexfinance.core.common.ui.composables.CategoryIcon
import ru.point.yandexfinance.core.common.ui.composables.GreyHorizontalDivider
import ru.point.yandexfinance.core.common.ui.composables.NoInternetBanner
import ru.point.yandexfinance.core.common.ui.composables.TransactionHistoryBrief
import ru.point.yandexfinance.core.common.utils.InternetHolder
import ru.point.yandexfinance.feature.transactions.ui.history.viewmodel.TransactionHistoryState
import ru.point.yandexfinance.feature.transactions.ui.history.viewmodel.TransactionHistoryViewModel
import ru.point.yandexfinance.ui.theme.CharcoalGrey
import ru.point.yandexfinance.ui.theme.GhostGray
import ru.point.yandexfinance.ui.theme.Graphite
import ru.point.yandexfinance.ui.theme.Mint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TransactionHistoryScreen(
    isIncome: Boolean,
    modifier: Modifier = Modifier
) {

    val viewModel: TransactionHistoryViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return TransactionHistoryViewModel(isIncome) as T
        }
    })

    val state by viewModel.composableState

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
                text = state.error!!.toUserMessage(),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(16.dp)
            )
        }

        else -> {
            TransactionHistoryList(
                state = state,
                modifier = modifier
            )
        }
    }
}

@Composable
fun TransactionHistoryList(
    state: TransactionHistoryState,
    modifier: Modifier = Modifier
) {
    val monthFormatter = remember {
        DateTimeFormatter.ofPattern("LLLL yyyy", Locale("ru"))
    }

    LazyColumn(modifier = modifier) {
        if (state.incomesHistory.isEmpty()) {
            item {
                Text(
                    text = "Нет доходов",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Graphite
                )
            }
            return@LazyColumn
        }
        item {
            TransactionHistoryBrief(
                startDate = LocalDate.now()
                    .format(monthFormatter)
                    .replaceFirstChar { it.titlecase(Locale("ru")) },
                endDate = state.incomesHistory
                    .first()
                    .transactionDate
                    .toTimeHHmm(),
                amount = state.incomesHistory.sumOf { it.amount.toAmountInt() }
                    .toFormattedCurrency(state.incomesHistory.first().currency),
                modifier = Modifier.fillMaxWidth()
            )
        }
        items(items = state.incomesHistory, key = { it.id }) { incomeHistoryItem ->
            BaseListItem(
                content = {
                    Column(horizontalAlignment = Alignment.Start) {
                        Text(
                            text = incomeHistoryItem.title,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Graphite
                        )
                        if (incomeHistoryItem.subtitle != null) {
                            Text(
                                text = incomeHistoryItem.subtitle,
                                style = MaterialTheme.typography.bodyMedium,
                                color = CharcoalGrey
                            )
                        }
                    }
                },
                lead = {
                    CategoryIcon(
                        title = incomeHistoryItem.title,
                        emojiIcon = incomeHistoryItem.emojiIcon,
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Mint)
                    )
                },
                trail = {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.End),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            horizontalAlignment = Alignment.End
                        ) {
                            Text(
                                text = incomeHistoryItem.amount
                                    .toFormattedCurrency(incomeHistoryItem.currency),
                                style = MaterialTheme.typography.bodyLarge,
                                color = Graphite
                            )
                            Text(
                                text = incomeHistoryItem.transactionDate.toTimeHHmm(),
                                style = MaterialTheme.typography.bodyMedium,
                                color = CharcoalGrey
                            )
                        }
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.more_icon),
                            contentDescription = null,
                            tint = GhostGray
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp)
            )
            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }
}