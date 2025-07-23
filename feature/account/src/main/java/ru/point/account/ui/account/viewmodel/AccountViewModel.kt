package ru.point.account.ui.account.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.account.domain.usecase.CalculateTransactionDiffUseCase
import ru.point.account.domain.usecase.GetAccountUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject


internal class AccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val calculateTransactionDiffUseCase: CalculateTransactionDiffUseCase,
) : MviViewModel<AccountState, AccountAction, Any>(AccountState()) {

    init {
        onHandleLoadAccountData()
        loadTransactionDiffs()
    }

    override fun reduce(action: AccountAction, state: AccountState): AccountState {
        return when (action) {
            is AccountAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is AccountAction.AccountLoadSuccess -> state.copy(
                isLoading = false,
                account = action.account
            )

            is AccountAction.LoadError -> state.copy(isLoading = false, error = action.error)
            is AccountAction.TransactionsDiffsLoadSuccess -> state.copy(transactionDiffs = action.transactionDiffs)
        }
    }

    private fun onHandleLoadAccountData() {
        handleAction<AccountAction.LoadRequested> {
            getAccountUseCase().fold(
                onSuccess = { account ->
                    onAction(AccountAction.AccountLoadSuccess(account))
                },
                onFailure = { error ->
                    onAction(AccountAction.LoadError(error.toAppError()))
                }
            )
        }
    }

    private fun loadTransactionDiffs() {
        viewModelScope.launch {
            calculateTransactionDiffUseCase().fold(
                onSuccess = {
                    onAction(AccountAction.TransactionsDiffsLoadSuccess(it))
                },
                onFailure = { error ->
                    onAction(AccountAction.LoadError(error.toAppError()))
                },
            )
        }
    }
}
