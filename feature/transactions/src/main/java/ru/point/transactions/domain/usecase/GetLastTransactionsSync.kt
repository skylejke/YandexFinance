package ru.point.transactions.domain.usecase

import kotlinx.coroutines.flow.map
import ru.point.api.repository.TransactionsRepository
import java.time.Instant
import javax.inject.Inject

internal class GetLastTransactionsSync @Inject constructor(
    private val transactionsRepository: TransactionsRepository
) {

    suspend operator fun invoke() = transactionsRepository.getLastSync().map {
        Instant.ofEpochMilli(it).toString()
    }
}