package ru.point.transactions.ui.editor.content

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.Flow
import ru.point.transactions.R
import ru.point.transactions.ui.editor.content.forms.AccountForm
import ru.point.transactions.ui.editor.content.forms.AmountForm
import ru.point.transactions.ui.editor.content.forms.CategoryForm
import ru.point.transactions.ui.editor.content.forms.CommentForm
import ru.point.transactions.ui.editor.content.forms.DateForm
import ru.point.transactions.ui.editor.content.forms.TimeForm
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorAction
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorEvent
import ru.point.transactions.ui.editor.viewmodel.state.TransactionEditorState
import ru.point.ui.colors.CharcoalGrey
import ru.point.ui.colors.CoralRed
import ru.point.ui.colors.White
import ru.point.ui.composables.EditTextDialog
import ru.point.ui.composables.GreyHorizontalDivider
import ru.point.ui.composables.LoadingIndicator
import ru.point.ui.composables.YandexFinanceDatePickerDialog
import ru.point.ui.composables.YandexFinanceTimePickerDialog
import ru.point.utils.extensions.extractNumericBalance
import ru.point.utils.extensions.showToast

@Composable
internal fun TransactionEditorScreenContent(
    state: TransactionEditorState,
    events: Flow<TransactionEditorEvent>,
    onAction: (TransactionEditorAction) -> Unit,
    onNavigate: () -> Unit,
    transactionId: Int?,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    var showAmountDialog by remember { mutableStateOf(false) }
    var showDatePickerDialog by remember { mutableStateOf(false) }
    var showTimePickerDialog by remember { mutableStateOf(false) }
    var showCategoryPickerDialog by remember { mutableStateOf(false) }
    var showErrorDialog by remember { mutableStateOf(false) }
    var showDeleteTransactionDialog by remember { mutableStateOf(false) }

    val baseModifier = Modifier
        .fillMaxWidth()
        .height(70.dp)

    val scrollState = rememberScrollState()

    Column(modifier = modifier.verticalScroll(scrollState)) {
        AccountForm(
            accountName = state.form.accountName,
            modifier = baseModifier.padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        CategoryForm(
            categoryName = state.form.categoryName,
            modifier = baseModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = {
                        onAction(TransactionEditorAction.Categories.CategoriesLoadRequested)
                        showCategoryPickerDialog = true
                    }
                )
                .padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        AmountForm(
            amountValue = state.form.amountValue,
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

        DateForm(
            dateValue = state.form.transactionDate,
            modifier = baseModifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = ripple(),
                    onClick = { showDatePickerDialog = true }
                )
                .padding(horizontal = 16.dp)
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        TimeForm(
            timeValue = state.form.transactionTime,
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

        CommentForm(
            commentValue = state.form.comment.orEmpty(),
            onCommentChanged = {
                onAction(TransactionEditorAction.Form.OnCommentChanged(comment = it))
            },
            modifier = baseModifier
        )

        GreyHorizontalDivider(modifier = Modifier.fillMaxWidth())

        if (transactionId != null) {
            Column {
                Button(
                    onClick = { showDeleteTransactionDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = CoralRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 32.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.delete_transaction),
                        color = White,
                    )
                }

                Text(
                    text = stringResource(R.string.last_sync_time, state.lastTimeSync),
                    style = MaterialTheme.typography.bodySmall,
                    color = CharcoalGrey,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        if (state.form.isActionLoading) {
            LoadingIndicator(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
        }
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                TransactionEditorEvent.ShowSuccessEditToastAndGoBack -> {
                    showToast(
                        context = context,
                        messageResId = if (transactionId != null) R.string.successfully_updated_transaction
                        else R.string.successfully_created_transaction
                    )
                    onNavigate()
                }

                TransactionEditorEvent.ShowSuccessDeleteToastAndGoBack -> {
                    showToast(
                        context = context,
                        messageResId = R.string.successfully_deleted_transaction
                    )
                    onNavigate()
                }

                TransactionEditorEvent.ShowErrorDialog -> {
                    showErrorDialog = true
                }

                TransactionEditorEvent.ShowInValidFieldsToast ->
                    showToast(
                        context = context,
                        messageResId = R.string.not_all_fields_correct,
                    )
            }
        }
    }

    if (showAmountDialog) {
        EditTextDialog(
            title = stringResource(R.string.update_amount),
            initialValue = state.form.amountValue.extractNumericBalance(),
            keyBoardType = KeyboardType.Number,
            onDismiss = {
                showAmountDialog = false
            },
            onConfirm = {
                onAction(TransactionEditorAction.Form.OnAmountChange(it))
                showAmountDialog = false
            },
            maxLength = 10
        )
    }

    if (showDatePickerDialog) {
        YandexFinanceDatePickerDialog(
            onDateSelected = {
                onAction(TransactionEditorAction.Form.OnDateChange(it))
                showDatePickerDialog = false
            },
            onDismiss = { showDatePickerDialog = false }
        )
    }

    if (showTimePickerDialog) {
        YandexFinanceTimePickerDialog(
            onTimeSelected = { formattedTime ->
                onAction(TransactionEditorAction.Form.OnTimeChange(formattedTime))
                showTimePickerDialog = false
            },
            onDismiss = { showTimePickerDialog = false }
        )
    }

    if (showCategoryPickerDialog) {
        CategoryPickerDialog(
            title = stringResource(R.string.choice_category),
            state = state.categories,
            onDismiss = { showCategoryPickerDialog = false },
            onConfirm = { categoryId, categoryName ->
                onAction(
                    TransactionEditorAction.Form.OnCategoryChange(
                        categoryId = categoryId,
                        categoryName = categoryName,
                    )
                )
                showCategoryPickerDialog = false
            },
            modifier = Modifier.height(400.dp)
        )
    }

    if (showErrorDialog) {
        ErrorDialog(
            onAction = {
                onAction(
                    if (transactionId != null) TransactionEditorAction.Update.OnUpdatePressed
                    else TransactionEditorAction.Create.OnCreatePressed
                )
                showErrorDialog = false
            },
            onDismiss = {
                showErrorDialog = false
            }
        )
    }

    if (showDeleteTransactionDialog) {
        DeleteTransactionDialog(
            onAction = {
                onAction(
                    TransactionEditorAction.Delete.OnDeletePressed
                )
                showDeleteTransactionDialog = false
            },
            onDismiss = {
                showDeleteTransactionDialog = false
            }
        )
    }
}
