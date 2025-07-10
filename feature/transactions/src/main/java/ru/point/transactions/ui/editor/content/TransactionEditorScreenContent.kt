package ru.point.transactions.ui.editor.content

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
import ru.point.transactions.R
import ru.point.transactions.ui.editor.content.forms.AccountForm
import ru.point.transactions.ui.editor.content.forms.AmountForm
import ru.point.transactions.ui.editor.content.forms.CategoryForm
import ru.point.transactions.ui.editor.content.forms.CommentForm
import ru.point.transactions.ui.editor.content.forms.DateForm
import ru.point.transactions.ui.editor.content.forms.TimeForm
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorAction
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorState
import ru.point.ui.composables.EditTextDialog
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.YandexFinanceDatePickerDialog
import ru.point.ui.composables.YandexFinanceTimePickerDialog
import ru.point.utils.extensions.extractNumericBalance

@Composable
internal fun TransactionEditorScreenContent(
    state: TransactionEditorState,
    onAction: (TransactionEditorAction) -> Unit,
    modifier: Modifier = Modifier,
) {

    var showAmountDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showCategoryPickerDialog by remember { mutableStateOf(false) }

    val baseModifier = Modifier
        .fillMaxWidth()
        .height(70.dp)

    LazyColumn(modifier = modifier) {
        item {
            AccountForm(
                accountName = state.accountName,
                modifier = baseModifier.padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        item {
            CategoryForm(
                categoryName = state.categoryName,
                modifier = baseModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showCategoryPickerDialog = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        item {
            AmountForm(
                amountValue = state.amountValue,
                modifier = baseModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showAmountDialog = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        item {
            DateForm(
                dateValue = state.transactionDate,
                modifier = baseModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = { showDatePickerDialog = true }
                    )
                    .padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        item {
            TimeForm(
                timeValue = state.transactionTime,
                modifier = baseModifier
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = {
                            showTimePickerDialog = true
                        }
                    )
                    .padding(horizontal = 16.dp)
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }

        item {
            CommentForm(
                commentValue = state.comment ?: "",
                onCommentChanged = {
                    onAction(TransactionEditorAction.OnCommentChanged(comment = it))
                },
                modifier = baseModifier
            )

            GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())
        }
    }

    if (showAmountDialog) {
        EditTextDialog(
            title = stringResource(R.string.update_amount),
            initialValue = state.amountValue.extractNumericBalance(),
            keyBoardType = KeyboardType.Number,
            onDismiss = {
                showAmountDialog = false
            },
            onConfirm = {
                onAction(TransactionEditorAction.OnAmountChange(it))
                showAmountDialog = false
            },
            maxLength = 10
        )
    }

    if (showDatePickerDialog) {
        YandexFinanceDatePickerDialog(
            onDateSelected = {
                onAction(TransactionEditorAction.OnDateChange(it))
                showDatePickerDialog = false
            },
            onDismiss = { showDatePickerDialog = false }
        )
    }

    if (showTimePickerDialog) {
        YandexFinanceTimePickerDialog(
            onTimeSelected = { formattedTime ->
                onAction(TransactionEditorAction.OnTimeChange(formattedTime))
                showTimePickerDialog = false
            },
            onDismiss = { showTimePickerDialog = false }
        )
    }

    if (showCategoryPickerDialog) {
        CategoryPickerDialog(
            title = stringResource(R.string.choice_category),
            categories = state.categoriesList,
            onDismiss = { showCategoryPickerDialog = false },
            onConfirm = {
                onAction(TransactionEditorAction.OnCategoryChange(it))
                showCategoryPickerDialog = false
            },
            modifier = Modifier.height(400.dp)
        )
    }
}
