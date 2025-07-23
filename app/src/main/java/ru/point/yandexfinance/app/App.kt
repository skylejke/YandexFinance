package ru.point.yandexfinance.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import ru.point.account.di.deps.AccountDepsStore
import ru.point.categories.di.deps.CategoriesDepsStore
import ru.point.impl.worker.SyncAccountWorker
import ru.point.impl.worker.SyncTransactionWorker
import ru.point.transactions.di.deps.TransactionDepsStore
import ru.point.utils.network.InternetHolder
import ru.point.yandexfinance.di.component.AppComponent
import ru.point.yandexfinance.di.component.DaggerAppComponent
import ru.point.yandexfinance.worker.YandexFinanceWorkerFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val SYNC_ACCOUNT_WORKER_NAME = "sync_account_worker"

private const val SYNC_TRANSACTION_WORKER_NAME = "sync_transaction_worker"

class App : Application(), Configuration.Provider {

    lateinit var appComponent: AppComponent

    @Inject
    lateinit var workerFactory: YandexFinanceWorkerFactory

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().bindContext(this).build()

        appComponent.inject(this)

        initDeps()

        InternetHolder.init(connectivityManager = getSystemService(ConnectivityManager::class.java))

        schedulePeriodicSync(this)
    }

    override val workManagerConfiguration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    private fun initDeps() {
        CategoriesDepsStore.categoriesDeps = appComponent
        AccountDepsStore.accountDeps = appComponent
        TransactionDepsStore.transactionDeps = appComponent
    }

    private fun schedulePeriodicSync(context: Context) {

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_ACCOUNT_WORKER_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = PeriodicWorkRequestBuilder<SyncAccountWorker>(
                repeatInterval = 2,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()
        )

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            uniqueWorkName = SYNC_TRANSACTION_WORKER_NAME,
            existingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.KEEP,
            request = PeriodicWorkRequestBuilder<SyncTransactionWorker>(
                repeatInterval = 2,
                repeatIntervalTimeUnit = TimeUnit.HOURS
            )
                .setConstraints(constraints)
                .build()
        )
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }