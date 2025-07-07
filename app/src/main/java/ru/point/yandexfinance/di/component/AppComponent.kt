package ru.point.yandexfinance.di.component

import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.categories.di.deps.CategoriesDeps
import ru.point.impl.di.AccountDataModule
import ru.point.impl.di.CategoriesDataModule
import ru.point.impl.di.TransactionsDataModule
import ru.point.network.di.NetworkModule
import ru.point.transactions.di.deps.TransactionDeps
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        AccountDataModule::class,
        CategoriesDataModule::class,
        TransactionsDataModule::class,
    ],
)
interface AppComponent: CategoriesDeps, AccountDeps, TransactionDeps