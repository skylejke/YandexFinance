package ru.point.account.domain.usecase

import ru.point.account.domain.model.AccountVo
import ru.point.account.domain.model.asAccountVo
import ru.point.api.repository.AccountRepository
import javax.inject.Inject

internal class GetAccountUseCase @Inject constructor(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() = accountRepository.getAccounts().map { accountDtos ->
        accountDtos.firstOrNull()?.asAccountVo ?: AccountVo()
    }
}