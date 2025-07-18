package ru.point.transactions.domain.usecase

import ru.point.api.repository.AccountRepository
import ru.point.transactions.domain.model.AccountData
import ru.point.transactions.domain.model.asAccountData
import javax.inject.Inject

internal class GetAccountDataUseCase @Inject constructor(
    private val accountRepository: AccountRepository,
) {

    suspend operator fun invoke() =
        accountRepository.getAccounts().map {
            it.firstOrNull()?.asAccountData ?: AccountData()
        }
}