package ru.point.yandexfinance.di.component

import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.categories.di.deps.CategoriesDeps
import ru.point.impl.di.NetworkModule
import ru.point.impl.di.RepositoryModule
import ru.point.transactions.di.deps.TransactionDeps
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
    ],
)
interface AppComponent: CategoriesDeps, AccountDeps, TransactionDeps