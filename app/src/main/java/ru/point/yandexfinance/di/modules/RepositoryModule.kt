package ru.point.yandexfinance.di.modules

import dagger.Binds
import dagger.Module
import ru.point.api.repository.AccountRepository
import ru.point.api.repository.CategoriesRepository
import ru.point.api.repository.TransactionsRepository
import ru.point.impl.repository.AccountRepositoryImpl
import ru.point.impl.repository.CategoriesRepositoryImpl
import ru.point.impl.repository.TransactionsRepositoryImpl
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindAccountRepository(accountRepositoryImpl: AccountRepositoryImpl): AccountRepository

    @Binds
    @Singleton
    fun bindTransactionsRepository(transactionsRepositoryImpl: TransactionsRepositoryImpl): TransactionsRepository

    @Binds
    @Singleton
    fun bindCategoriesRepository(categoriesRepositoryImpl: CategoriesRepositoryImpl): CategoriesRepository
}