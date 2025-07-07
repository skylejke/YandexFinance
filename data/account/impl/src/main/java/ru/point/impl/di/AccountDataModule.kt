package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.AccountRepository
import ru.point.impl.repository.AccountRepositoryImpl
import ru.point.impl.service.AccountService
import javax.inject.Singleton

@Module
class AccountDataModule {

    @Provides
    @Singleton
    internal fun provideAccountService(retrofit: Retrofit) =
        retrofit.create<AccountService>()

    @Singleton
    @Provides
    internal fun provideAccountRepository(accountService: AccountService): AccountRepository =
        AccountRepositoryImpl(accountService = accountService)
}