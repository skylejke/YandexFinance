package ru.point.yandexfinance.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import ru.point.api.repository.AccountRepository
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.worker.SyncAccountWorker
import ru.point.impl.worker.SyncTransactionWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class YandexFinanceWorkerFactory @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val accountRepository: AccountRepository,
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? =
        when (workerClassName) {
            SyncTransactionWorker::class.qualifiedName ->
                SyncTransactionWorker(
                    context = appContext,
                    workerParameters = workerParameters,
                    transactionsRepository = transactionsRepository
                )

            SyncAccountWorker::class.qualifiedName ->
                SyncAccountWorker(
                    context = appContext,
                    workerParameters = workerParameters,
                    accountRepository = accountRepository
                )

            else -> null
        }
}