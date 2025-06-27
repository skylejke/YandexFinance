package ru.point.account.domain.usecase

import ru.point.account.domain.model.AccountVo

interface GetAccountUseCase {
    suspend operator fun invoke(): Result<AccountVo>
}