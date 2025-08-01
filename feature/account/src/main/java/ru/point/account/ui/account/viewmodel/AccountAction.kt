package ru.point.account.ui.account.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.account.domain.model.AccountVo
import ru.point.utils.model.AppError
import ru.point.vo.TransactionDiff

@Immutable
internal sealed interface AccountAction {
    data object LoadRequested : AccountAction

    data class LoadError(val error: AppError) : AccountAction

    data class AccountLoadSuccess(val account: AccountVo) : AccountAction

    data class TransactionsDiffsLoadSuccess(val transactionDiffs: List<TransactionDiff>): AccountAction
}
