package ru.point.account.ui.update.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.point.account.domain.model.AccountVo
import ru.point.account.domain.usecase.GetAccountUseCase
import ru.point.account.domain.usecase.UpdateAccountUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.extensions.extractNumericBalance
import ru.point.utils.extensions.toCurrencyCode
import ru.point.utils.model.toAppError
import javax.inject.Inject

class UpdateAccountViewModel @Inject constructor(
    private val getAccountUseCase: GetAccountUseCase,
    private val updateAccountUseCase: UpdateAccountUseCase
) : MviViewModel<UpdateAccountState, UpdateAccountAction, UpdateAccountEvent>(initialState = UpdateAccountState()) {

    init {
        loadAccount()
        handleOnUpdate()
    }

    override fun reduce(action: UpdateAccountAction, state: UpdateAccountState): UpdateAccountState {
        return when (action) {
            is UpdateAccountAction.LoadRequested -> state.copy(isLoading = true, error = null)

            is UpdateAccountAction.LoadSuccess -> state.copy(
                isLoading = false,
                name = action.account.name,
                balance = action.account.balance,
                currency = action.account.currency
            )

            is UpdateAccountAction.LoadError -> state.copy(isLoading = false, error = action.error)

            is UpdateAccountAction.OnAccountBalanceEntered -> state.copy(balance = action.accountBalance)

            is UpdateAccountAction.OnAccountCurrencyEntered -> state.copy(currency = action.accountCurrency)

            is UpdateAccountAction.OnAccountNameEntered -> state.copy(name = action.accountName)

            UpdateAccountAction.OnUpdatePressed -> state
        }
    }

    private fun handleOnUpdate() {
        handleAction<UpdateAccountAction.OnUpdatePressed> {
            val localState = state.value
            updateAccountUseCase(
                account = AccountVo(
                    name = localState.name,
                    balance = localState.balance.extractNumericBalance(),
                    currency = localState.currency.toCurrencyCode()
                )
            ).fold(
                onSuccess = {
                    onEvent(UpdateAccountEvent.ShowSuccessToastAndGoBack)
                },
                onFailure = {
                    onEvent(UpdateAccountEvent.ShowErrorToast)
                }
            )
        }
    }

    private fun loadAccount() {
        viewModelScope.launch {
            onAction(UpdateAccountAction.LoadRequested)

            getAccountUseCase().fold(
                onSuccess = { account ->
                    onAction(UpdateAccountAction.LoadSuccess(account))
                },
                onFailure = { error ->
                    onAction(UpdateAccountAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}