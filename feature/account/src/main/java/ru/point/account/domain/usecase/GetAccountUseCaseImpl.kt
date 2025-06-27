package ru.point.account.domain.usecase

import ru.point.account.domain.model.asAccountVo
import ru.point.data.repository.account.AccountRepository
import javax.inject.Inject

class GetAccountUseCaseImpl @Inject constructor(
    private val accountRepository: AccountRepository
) : GetAccountUseCase {
    override suspend operator fun invoke() = accountRepository.getAccounts().map { accountDtos ->
            accountDtos.first().asAccountVo
        }
}