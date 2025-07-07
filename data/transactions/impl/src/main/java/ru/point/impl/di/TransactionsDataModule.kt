package ru.point.impl.di

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.create
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.repository.TransactionsRepositoryImpl
import ru.point.impl.service.TransactionsService
import javax.inject.Singleton

@Module
class TransactionsDataModule {

    @Provides
    @Singleton
    internal fun provideTransactionsService(retrofit: Retrofit) =
        retrofit.create<TransactionsService>()

    @Singleton
    @Provides
    internal fun provideTransactionsRepository(transactionsService: TransactionsService): TransactionsRepository =
        TransactionsRepositoryImpl(transactionsService = transactionsService)
}