package ru.point.account.ui.update.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import ru.point.account.R
import ru.point.account.ui.update.viewmodel.UpdateAccountAction
import ru.point.account.ui.update.viewmodel.UpdateAccountState
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.utils.extensions.extractNumericBalance
import ru.point.utils.extensions.toFormattedCurrency

const val DEFAULT_ACCOUNT_NAME = "Мой счет"

@Composable
internal fun UpdateAccountScreenContent(
    state: UpdateAccountState,
    onAction: (UpdateAccountAction) -> Unit,
    modifier: Modifier = Modifier
) {

    var showNameDialog by remember { mutableStateOf(false) }
    var showBalanceDialog by remember { mutableStateOf(false) }
    var showCurrencyPicker by remember { mutableStateOf(false) }

    val editAccountCardModifier = Modifier
        .fillMaxWidth()
        .height(57.dp)

    LazyColumn(modifier = modifier) {
        item {
            UpdateAccountName(
                accountName = state.name,
                modifier = editAccountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showNameDialog = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

            UpdateAccountBalanceCard(
                accountBalance = state.balance.toFormattedCurrency(state.currency),
                modifier = editAccountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showBalanceDialog = true
                        }
                    )
                    .padding(horizontal = 16.dp),
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

            UpdateAccountCurrencyCard(
                accountCurrency = state.currency,
                modifier = editAccountCardModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showCurrencyPicker = true
                        }
                    )
                    .padding(horizontal = 16.dp))

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }

    if (showNameDialog) {
        EditTextDialog(
            title = stringResource(R.string.update_name),
            initialValue = state.name,
            keyBoardType = KeyboardType.Text,
            onDismiss = {
                showNameDialog = false
            },
            onConfirm = {
                onAction(UpdateAccountAction.OnAccountNameEntered(it.ifEmpty { DEFAULT_ACCOUNT_NAME }))
                showNameDialog = false
            },
            maxLength = 20
        )
    }

    if (showBalanceDialog) {
        EditTextDialog(
            title = stringResource(R.string.update_balance),
            initialValue = state.balance.extractNumericBalance(),
            keyBoardType = KeyboardType.Number,
            onDismiss = {
                showBalanceDialog = false
            },
            onConfirm = {
                onAction(UpdateAccountAction.OnAccountBalanceEntered(it))
                showBalanceDialog = false
            },
            maxLength = 10
        )
    }

    if (showCurrencyPicker) {
        CurrencyPickerBottomSheet(
            onDismiss = { showCurrencyPicker = false },
            onCurrencySelected = {
                onAction(UpdateAccountAction.OnAccountCurrencyEntered(it))
                showCurrencyPicker = false
            }
        )
    }
}