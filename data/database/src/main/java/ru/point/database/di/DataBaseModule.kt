package ru.point.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.point.api.repository.AccountRepository
import ru.point.api.repository.CategoriesRepository
import ru.point.api.repository.TransactionsRepository
import ru.point.database.YandexFinanceDatabase
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.TransactionsDao
import ru.point.database.repo.LocalDatabaseRepository
import ru.point.database.repo.LocalDatabaseRepositoryImpl
import ru.point.utils.network.InternetTracker
import javax.inject.Singleton

@Module
class DataBaseModule {

    @[Provides Singleton]
    internal fun provideDataBase(context: Context) = YandexFinanceDatabase.Companion.getDataBase(context = context)

    @[Provides Singleton]
    internal fun provideAccountDao(database: YandexFinanceDatabase) = database.getAccountDao()

    @[Provides Singleton]
    internal fun provideTransactionsDao(database: YandexFinanceDatabase) = database.getTransactionsDao()

    @[Provides Singleton]
    internal fun provideCategoriesDao(database: YandexFinanceDatabase) = database.getCategoriesDao()

    @[Provides Singleton]
    internal fun providePendingTransactionsDao(database: YandexFinanceDatabase) = database.getPendingTransactionsDao()

    @[Provides Singleton]
    internal fun provideLocalDatabaseRepository(
       internetTracker: InternetTracker,
       transactionsDao: TransactionsDao,
       categoriesDao: CategoriesDao,
       accountDao: AccountDao,
       transactionsRepository: TransactionsRepository,
       accountRepository: AccountRepository,
       categoriesRepository: CategoriesRepository,
    ): LocalDatabaseRepository = LocalDatabaseRepositoryImpl(
        internetTracker = internetTracker,
        transactionsDao = transactionsDao,
        categoriesDao = categoriesDao,
        accountDao = accountDao,
        transactionsRepository = transactionsRepository,
        accountRepository = accountRepository,
        categoriesRepository = categoriesRepository,
    )
}