package ru.point.impl.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ru.point.api.repository.AccountRepository
import ru.point.impl.repository.AccountRepositoryImpl

class SyncAccountWorker(
    context: Context,
    workerParameters: WorkerParameters,
    private val accountRepository: AccountRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork() = try {
        (accountRepository as? AccountRepositoryImpl)?.syncPendingUpdate()
        Result.success()
    } catch (_: Throwable) {
        Result.retry()
    }
}