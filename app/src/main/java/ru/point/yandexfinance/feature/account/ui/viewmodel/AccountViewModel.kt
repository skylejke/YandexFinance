package ru.point.yandexfinance.feature.account.ui.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.ui.MviViewModel
import ru.point.yandexfinance.feature.account.model.toAccount
import ru.point.yandexfinance.network.RetrofitInstance

class AccountViewModel : MviViewModel<AccountState, AccountAction, Any>(AccountState()) {

    init {
        loadAccount()
    }

    override fun reduce(action: AccountAction, state: AccountState): AccountState {
        return when (action) {
            is AccountAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is AccountAction.LoadSuccess -> state.copy(isLoading = false, account = action.account)
            is AccountAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            onAction(AccountAction.LoadRequested)

            RetrofitInstance.accountApi.getAccounts().fold(
                onSuccess = { accounts ->
                    onAction(AccountAction.LoadSuccess(accounts[0].toAccount))
                },
                onFailure = { error ->
                    onAction(AccountAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}
