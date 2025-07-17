package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.TransactionsRepository
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.PendingTransactionsDao
import ru.point.database.dao.TransactionsDao
import ru.point.impl.repository.TransactionsRepositoryImpl
import ru.point.impl.service.TransactionsService
import ru.point.utils.network.InternetTracker
import javax.inject.Singleton

@Module
class TransactionsDataModule {

    @[Provides Singleton]
    internal fun provideTransactionsService(retrofit: Retrofit) =
        retrofit.create<TransactionsService>()

    @[Provides Singleton]
    internal fun provideTransactionsRepository(
        transactionsService: TransactionsService,
        transactionsDao: TransactionsDao,
        pendingTransactionsDao: PendingTransactionsDao,
        accountDao: AccountDao,
        categoriesDao: CategoriesDao,
        internetTracker: InternetTracker
    ): TransactionsRepository =
        TransactionsRepositoryImpl(
            transactionsService = transactionsService,
            transactionsDao = transactionsDao,
            pendingTransactionsDao = pendingTransactionsDao,
            accountDao = accountDao,
            categoriesDao = categoriesDao,
            internetTracker = internetTracker,
        )
}