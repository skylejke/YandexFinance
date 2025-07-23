package ru.point.transactions.ui.editor.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.Lazy
import kotlinx.coroutines.launch
import ru.point.transactions.domain.model.TransactionRequestVo
import ru.point.transactions.domain.usecase.CreateTransactionUseCase
import ru.point.transactions.domain.usecase.DeleteTransactionUseCase
import ru.point.transactions.domain.usecase.GetAccountDataUseCase
import ru.point.transactions.domain.usecase.GetCategoriesByTypeUseCase
import ru.point.transactions.domain.usecase.GetLastTransactionsSync
import ru.point.transactions.domain.usecase.GetTransactionUseCase
import ru.point.transactions.domain.usecase.UpdateTransactionUseCase
import ru.point.transactions.ui.editor.viewmodel.state.FormState
import ru.point.transactions.ui.editor.viewmodel.state.TransactionEditorState
import ru.point.ui.MviViewModel
import ru.point.utils.extensions.extractNumericBalance
import ru.point.utils.extensions.fullDateFormatter
import ru.point.utils.extensions.timeFormatter
import ru.point.utils.extensions.toFormattedCurrency
import ru.point.utils.extensions.toReadableDateTimeWithSeconds
import ru.point.utils.model.toAppError
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import javax.inject.Inject

internal class TransactionEditorViewModel @Inject constructor(
    private val getTransactionUseCase: GetTransactionUseCase,
    private val getCategoriesByTypeUseCase: GetCategoriesByTypeUseCase,
    private val getAccountDataUseCase: GetAccountDataUseCase,
    private val getLastTransactionsSync: GetLastTransactionsSync,
    private val createTransactionUseCase: Lazy<CreateTransactionUseCase>,
    private val updateTransactionUseCase: Lazy<UpdateTransactionUseCase>,
    private val deleteTransactionUseCase: Lazy<DeleteTransactionUseCase>,
    private val transactionId: Int?,
    private val isIncome: Boolean,
) : MviViewModel<TransactionEditorState, TransactionEditorAction, TransactionEditorEvent>(
    initialState = TransactionEditorState(
        form = FormState(
            transactionDate = LocalDate.now().format(fullDateFormatter),
            transactionTime = LocalTime.now().format(timeFormatter)
        )
    )
) {

    init {
        loadAccountData()
        if (transactionId != null) {
            loadTransaction()
            getLastTimeSync()
            onHandleUpdate()
            onHandleDelete()
        } else {
            onHandleCreate()
        }
        onHandleLoadCategories()
    }

    override fun reduce(action: TransactionEditorAction, state: TransactionEditorState) = when (action) {

        is TransactionEditorAction.LastTimeSync.OnGetLastTimeSync -> state.copy(lastTimeSync = action.lastTimeSync)

        is TransactionEditorAction.Initial.TransactionLoadSuccess ->
            with(action.transactionVo) {
                val updatedForm = state.form.copy(
                    accountName = accountName,
                    categoryId = categoryId,
                    categoryName = categoryName,
                    amountValue = amount.toFormattedCurrency(currency),
                    currency = currency,
                    transactionDate = transactionDate,
                    transactionTime = transactionTime,
                    comment = comment,
                    isInitialLoading = false,
                )
                state.copy(form = updatedForm)
            }

        is TransactionEditorAction.Initial.InitialLoadRequested ->
            state.copy(form = state.form.copy(isInitialLoading = true))

        is TransactionEditorAction.Initial.InitialLoadError ->
            state.copy(form = state.form.copy(isInitialLoading = false, error = action.error))

        is TransactionEditorAction.Initial.AccountDataLoadSuccess -> with(action.accountData) {
            state.copy(form = state.form.copy(accountName = accountName, currency = currency, isInitialLoading = false))
        }

        is TransactionEditorAction.Form.OnCommentChanged ->
            state.copy(form = state.form.copy(comment = action.comment))

        is TransactionEditorAction.Form.OnAmountChange ->
            state.copy(form = state.form.copy(amountValue = action.amount.toFormattedCurrency(state.form.currency)))

        is TransactionEditorAction.Form.OnDateChange ->
            state.copy(form = state.form.copy(transactionDate = action.date))

        is TransactionEditorAction.Form.OnTimeChange ->
            state.copy(form = state.form.copy(transactionTime = action.time))

        is TransactionEditorAction.Form.OnCategoryChange ->
            state.copy(form = state.form.copy(categoryId = action.categoryId, categoryName = action.categoryName))

        is TransactionEditorAction.Categories.CategoriesLoadRequested ->
            state.copy(categories = state.categories.copy(isCategoriesLoading = true))

        is TransactionEditorAction.Categories.CategoriesLoadSuccess ->
            state.copy(
                categories = state.categories.copy(
                    categoriesList = action.categories,
                    isCategoriesLoading = false
                )
            )

        is TransactionEditorAction.Categories.CategoriesLoadError ->
            state.copy(
                categories = state.categories.copy(
                    categoriesLoadError = action.error,
                    isCategoriesLoading = false
                )
            )

        is TransactionEditorAction.Create.OnCreatePressed,
        TransactionEditorAction.Update.OnUpdatePressed,
        TransactionEditorAction.Delete.OnDeletePressed -> state.copy(form = state.form.copy(isActionLoading = true))

        is TransactionEditorAction.Create.OnCreateError,
        TransactionEditorAction.Create.OnCreateSuccess,
        TransactionEditorAction.Update.OnUpdateError,
        TransactionEditorAction.Update.OnUpdateSuccess,
        TransactionEditorAction.Delete.OnDeleteSuccess,
        TransactionEditorAction.Delete.OnDeleteError -> state.copy(form = state.form.copy(isActionLoading = false))
    }

    private fun loadTransaction() {
        viewModelScope.launch {
            getTransactionUseCase(transactionId!!).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Initial.TransactionLoadSuccess(transactionVo = it))
                },
                onFailure = {
                    onAction(TransactionEditorAction.Initial.InitialLoadError(it.toAppError()))
                }
            )
        }
    }

    private fun onHandleLoadCategories() {
        handleAction<TransactionEditorAction.Categories.CategoriesLoadRequested> {
            getCategoriesByTypeUseCase(isIncome = isIncome).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Categories.CategoriesLoadSuccess(it))
                },
                onFailure = {
                    onAction(TransactionEditorAction.Categories.CategoriesLoadError(it.toAppError()))
                }
            )
        }
    }

    private fun loadAccountData() {
        viewModelScope.launch {
            onAction(TransactionEditorAction.Initial.InitialLoadRequested)
            getAccountDataUseCase().fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Initial.AccountDataLoadSuccess(it))
                },
                onFailure = {
                    onAction(TransactionEditorAction.Initial.InitialLoadError(it.toAppError()))
                }
            )
        }
    }

    private fun onHandleCreate() {
        handleAction<TransactionEditorAction.Create.OnCreatePressed> {
            val localState = state.value.form

            if (!localState.amountValue.extractNumericBalance().isValidAmount()
                || localState.categoryName.isEmpty()
            ) {
                onAction(TransactionEditorAction.Update.OnUpdateError)
                onEvent(TransactionEditorEvent.ShowInValidFieldsToast)
                return@handleAction
            }

            createTransactionUseCase.get()(
                transactionRequestVo = TransactionRequestVo(
                    categoryId = localState.categoryId!!,
                    amount = localState.amountValue.extractNumericBalance(),
                    transactionDate = formatToIso8601(localState.transactionDate, localState.transactionTime),
                    comment = localState.comment,
                )
            ).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Create.OnCreateSuccess)
                    onEvent(TransactionEditorEvent.ShowSuccessEditToastAndGoBack)
                },
                onFailure = {
                    onAction(TransactionEditorAction.Create.OnCreateError)
                    onEvent(TransactionEditorEvent.ShowErrorDialog)
                }
            )
        }
    }

    private fun onHandleUpdate() {
        handleAction<TransactionEditorAction.Update.OnUpdatePressed> {
            val localState = state.value.form

            if (!localState.amountValue.extractNumericBalance().isValidAmount()
                || localState.categoryName.isEmpty()
            ) {
                onAction(TransactionEditorAction.Update.OnUpdateError)
                onEvent(TransactionEditorEvent.ShowInValidFieldsToast)
                return@handleAction
            }

            updateTransactionUseCase.get()(
                transactionId = transactionId!!,
                transactionRequestVo = TransactionRequestVo(
                    categoryId = localState.categoryId!!,
                    amount = localState.amountValue.extractNumericBalance(),
                    transactionDate = formatToIso8601(localState.transactionDate, localState.transactionTime),
                    comment = localState.comment
                )
            ).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Update.OnUpdateSuccess)
                    onEvent(TransactionEditorEvent.ShowSuccessEditToastAndGoBack)
                },
                onFailure = {
                    onAction(TransactionEditorAction.Update.OnUpdateError)
                    onEvent(TransactionEditorEvent.ShowErrorDialog)
                }
            )
        }
    }

    private fun onHandleDelete() {
        handleAction<TransactionEditorAction.Delete.OnDeletePressed> {
            deleteTransactionUseCase.get()(
                transactionId = transactionId!!
            ).fold(
                onSuccess = {
                    onAction(TransactionEditorAction.Delete.OnDeleteSuccess)
                    onEvent(TransactionEditorEvent.ShowSuccessDeleteToastAndGoBack)
                },
                onFailure = {
                    onAction(TransactionEditorAction.Delete.OnDeleteError)
                    onEvent(TransactionEditorEvent.ShowErrorDialog)
                }
            )
        }
    }

    private fun getLastTimeSync() {
        viewModelScope.launch {
            getLastTransactionsSync().collect {
                onAction(TransactionEditorAction.LastTimeSync.OnGetLastTimeSync(lastTimeSync = it.toReadableDateTimeWithSeconds()))
            }
        }
    }

    private fun formatToIso8601(date: String, time: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
        val dateTime = LocalDateTime.parse("$date $time", inputFormatter)

        return dateTime.atOffset(ZoneOffset.UTC)
            .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)
    }

    private fun String.isValidAmount(): Boolean {
        val normalized = this.replace(" ", "").trim()
        if (normalized.isEmpty()) return false
        return normalized.toDouble() > 0
    }
}