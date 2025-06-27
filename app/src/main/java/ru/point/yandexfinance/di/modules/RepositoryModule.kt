package ru.point.yandexfinance.di.modules

import dagger.Binds
import dagger.Module
import ru.point.data.repository.account.AccountRepository
import ru.point.data.repository.account.AccountRepositoryImpl
import ru.point.data.repository.categories.CategoriesRepository
import ru.point.data.repository.categories.CategoriesRepositoryImpl
import ru.point.data.repository.transactions.TransactionsRepository
import ru.point.data.repository.transactions.TransactionsRepositoryImpl
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