package ru.point.account.domain.usecase

import ru.point.account.domain.model.AccountVo
import ru.point.account.domain.model.asAccountDto
import ru.point.api.repository.AccountRepository
import javax.inject.Inject

class UpdateAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {

    suspend operator fun invoke(account: AccountVo) =
        accountRepository.updateAccount(accountDto = account.asAccountDto)
}