package ru.point.account.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.point.account.ui.viewmodel.AccountState
import ru.point.ui.colors.Mint
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.utils.extensions.toCurrencySymbol
import ru.point.utils.extensions.toFormattedCurrency

@Composable
internal fun AccountScreenContent(
    state: AccountState,
    modifier: Modifier = Modifier,
) {
    val accountCardModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)
        .background(Mint)

    LazyColumn(modifier = modifier) {
        item(key = state.account?.id) {

            AccountBalance(
                trailingText = state.account?.balance ?: "",
                modifier = accountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp),
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

            AccountCurrency(
                trailingText = state.account?.currency ?: "",
                modifier = accountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {}
                    )
                    .padding(horizontal = 16.dp))
        }
    }
}