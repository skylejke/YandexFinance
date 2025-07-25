package ru.point.account.ui.account.viewmodel

import androidx.compose.runtime.Immutable
import ru.point.account.domain.model.AccountVo
import ru.point.utils.model.AppError
import ru.point.vo.TransactionDiff

@Immutable
internal data class AccountState(
    val isLoading: Boolean = true,
    val account: AccountVo? = null,
    val error: AppError? = null,
    val transactionDiffs: List<TransactionDiff> = emptyList(),
)
