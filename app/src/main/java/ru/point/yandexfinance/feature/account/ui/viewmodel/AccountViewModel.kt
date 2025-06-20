package ru.point.yandexfinance.feature.account.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.point.yandexfinance.core.common.model.toAppError
import ru.point.yandexfinance.core.common.model.toUserMessage
import ru.point.yandexfinance.feature.account.model.toAccount
import ru.point.yandexfinance.network.RetrofitInstance

class AccountViewModel : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state = _state.asStateFlow()

    private val _actions = MutableSharedFlow<AccountAction>()
    val actions get() = _actions.asSharedFlow()

    init {
        viewModelScope.launch {
            _actions.collect { action ->
                _state.update { reduce(action, it) }
            }
        }

        loadAccount()
    }

    fun onAction(action: AccountAction) {
        viewModelScope.launch {
            _actions.emit(action)
        }
    }

    private fun reduce(action: AccountAction, state: AccountState): AccountState {
        return when (action) {
            is AccountAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is AccountAction.LoadSuccess -> state.copy(isLoading = false, account = action.account)
            is AccountAction.LoadError -> state.copy(isLoading = false, error = action.message)
        }
    }

    private fun loadAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            _actions.emit(AccountAction.LoadRequested)

            RetrofitInstance.accountApi.getAccounts().fold(
                onSuccess = { accounts ->
                    _actions.emit(AccountAction.LoadSuccess(accounts[0].toAccount))
                },
                onFailure = { error ->
                    _actions.emit(AccountAction.LoadError(error.toAppError().toUserMessage()))
                }
            )
        }
    }
}
