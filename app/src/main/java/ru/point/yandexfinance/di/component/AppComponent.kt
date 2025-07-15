package ru.point.yandexfinance.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.categories.di.deps.CategoriesDeps
import ru.point.database.di.DataBaseModule
import ru.point.impl.di.AccountDataModule
import ru.point.impl.di.CategoriesDataModule
import ru.point.impl.di.TransactionsDataModule
import ru.point.network.di.NetworkModule
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.yandexfinance.ui.App
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        NetworkModule::class,
        DataBaseModule::class,
        AccountDataModule::class,
        CategoriesDataModule::class,
        TransactionsDataModule::class,
    ],
)]
interface AppComponent : CategoriesDeps, AccountDeps, TransactionDeps{
    @Component.Builder
    interface Builder {
        /**
         * Скажи Dagger’у: “Беру Context у пользователя при сборке графа”
         */
        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): AppComponent
    }

    // Точка входа в ваш граф, например:
    fun inject(app: App)
}