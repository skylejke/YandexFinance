package ru.point.impl.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.repository.TransactionsRepositoryImpl

class SyncTransactionWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val transactionsRepository: TransactionsRepository,
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork() = try {
        (transactionsRepository as? TransactionsRepositoryImpl)?.syncPendingTransaction()
        Result.success()
    } catch (_: Throwable) {
        Result.retry()
    }
}