package ru.point.account.ui.account.viewmodel

import ru.point.account.domain.usecase.GetAccountUseCase
import ru.point.ui.MviViewModel
import ru.point.utils.model.toAppError
import javax.inject.Inject

/**
 * ViewModel, отвечающая за загрузку и управление состоянием данных аккаунта пользователя.
 *
 * Выполняет вызов [GetAccountUseCase], обрабатывает результаты и преобразует их в UI-состояние
 * с помощью паттерна MVI.
 */
class AccountViewModel @Inject constructor(private val getAccountUseCase: GetAccountUseCase) :
    MviViewModel<AccountState, AccountAction, Any>(AccountState()) {

    init {
        onHandleLoad()
    }

    override fun reduce(action: AccountAction, state: AccountState): AccountState {
        return when (action) {
            is AccountAction.LoadRequested -> state.copy(isLoading = true, error = null)
            is AccountAction.LoadSuccess -> state.copy(isLoading = false, account = action.account)
            is AccountAction.LoadError -> state.copy(isLoading = false, error = action.error)
        }
    }

    private fun onHandleLoad() {
        handleAction<AccountAction.LoadRequested> {
            getAccountUseCase().fold(
                onSuccess = { account ->
                    onAction(AccountAction.LoadSuccess(account))
                },
                onFailure = { error ->
                    onAction(AccountAction.LoadError(error.toAppError()))
                }
            )
        }
    }
}
