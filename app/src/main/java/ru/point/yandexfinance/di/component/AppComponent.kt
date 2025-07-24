package ru.point.yandexfinance.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.categories.di.deps.CategoriesDeps
import ru.point.database.di.DataBaseModule
import ru.point.impl.di.AccountDataModule
import ru.point.impl.di.CategoriesDataModule
import ru.point.impl.di.SettingsDataModule
import ru.point.impl.di.TransactionsDataModule
import ru.point.network.di.NetworkModule
import ru.point.settings.di.deps.SettingsDeps
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.yandexfinance.app.App
import ru.point.yandexfinance.ui.MainActivity
import javax.inject.Singleton

@[Singleton Component(
    modules = [
        NetworkModule::class,
        DataBaseModule::class,
        AccountDataModule::class,
        CategoriesDataModule::class,
        TransactionsDataModule::class,
        SettingsDataModule::class,
    ],
)]
interface AppComponent : CategoriesDeps, AccountDeps, TransactionDeps, SettingsDeps {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun bindContext(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(mainActivity: MainActivity)
}