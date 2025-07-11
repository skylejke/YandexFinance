package ru.point.yandexfinance.ui

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import ru.point.account.di.deps.AccountDepsStore
import ru.point.categories.di.deps.CategoriesDepsStore
import ru.point.transactions.di.deps.TransactionDepsStore
import ru.point.utils.network.InternetHolder
import ru.point.yandexfinance.di.component.AppComponent
import ru.point.yandexfinance.di.component.DaggerAppComponent

/**
 * Класс приложения, инициализирующий корневой Dagger-компонент [AppComponent] и трекер сети [InternetHolder].
 *
 * Используется как точка входа для внедрения зависимостей и отслеживания состояния подключения к интернету.
 */
class App : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.create()

        CategoriesDepsStore.categoriesDeps = appComponent
        AccountDepsStore.accountDeps = appComponent
        TransactionDepsStore.transactionDeps = appComponent

        InternetHolder.init(connectivityManager = getSystemService(ConnectivityManager::class.java))
    }
}

/**
 * Расширение для получения [AppComponent] из любого контекста.
 *
 * Позволяет безопасно получать зависимостный граф из [Context], включая вложенные контексты.
 */
val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }