package ru.point.impl.di

import dagger.Module
import dagger.Provides
import ru.point.api.repository.AccountRepository
import ru.point.api.repository.CategoriesRepository
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.repository.AccountRepositoryImpl
import ru.point.impl.repository.CategoriesRepositoryImpl
import ru.point.impl.repository.TransactionsRepositoryImpl
import ru.point.impl.service.AccountService
import ru.point.impl.service.CategoriesService
import ru.point.impl.service.TransactionsService
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    internal fun bindAccountRepository(accountService: AccountService): AccountRepository =
        AccountRepositoryImpl(accountService = accountService)

    @Singleton
    @Provides
    internal fun bindTransactionsRepository(transactionsService: TransactionsService): TransactionsRepository =
        TransactionsRepositoryImpl(transactionsService = transactionsService)

    @Singleton
    @Provides
    internal fun bindCategoriesRepository(categoriesService: CategoriesService): CategoriesRepository =
        CategoriesRepositoryImpl(categoriesService = categoriesService)
}