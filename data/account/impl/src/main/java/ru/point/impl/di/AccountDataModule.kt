package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.AccountRepository
import ru.point.database.dao.AccountDao
import ru.point.database.dao.TransactionsDao
import ru.point.impl.repository.AccountRepositoryImpl
import ru.point.impl.service.AccountService
import ru.point.utils.network.InternetTracker
import javax.inject.Singleton

@Module
class AccountDataModule {

    @[Provides Singleton]
    internal fun provideAccountService(retrofit: Retrofit) =
        retrofit.create<AccountService>()

    @[Provides Singleton]
    internal fun provideAccountRepository(
        accountService: AccountService,
        accountDao: AccountDao,
        transactionsDao: TransactionsDao,
        internetTracker: InternetTracker
    ): AccountRepository =
        AccountRepositoryImpl(
            accountService = accountService,
            accountDao = accountDao,
            transactionsDao = transactionsDao,
            internetTracker = internetTracker
        )
}