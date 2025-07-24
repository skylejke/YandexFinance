package ru.point.account.ui.account.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.account.ui.account.viewmodel.AccountState
import ru.point.charts.TransactionsDiffsGraph
import ru.point.ui.composables.GreyHorizontalDivider

@Composable
internal fun AccountScreenContent(
    state: AccountState,
    modifier: Modifier = Modifier,
) {
    val accountCardModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(MaterialTheme.colorScheme.secondaryContainer)
        .padding(horizontal = 16.dp)

    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        AccountName(
            name = state.account?.name.orEmpty(),
            modifier = accountCardModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AccountBalance(
            balance = state.account?.balance.orEmpty(),
            modifier = accountCardModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AccountCurrency(
            currency = state.account?.currency.orEmpty(),
            modifier = accountCardModifier
        )

        TransactionsDiffsGraph(
            transactionDiffs = state.transactionDiffs,
            modifier = Modifier
                .fillMaxWidth()
                .height(233.dp)
                .padding(16.dp)
        )
    }
}