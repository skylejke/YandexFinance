package ru.point.database.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.point.database.YandexFinanceDatabase
import javax.inject.Singleton

@Module
class DataBaseModule {

    @[Provides Singleton]
    internal fun provideDataBase(context: Context) = YandexFinanceDatabase.Companion.getDataBase(context = context)

    @[Provides Singleton]
    internal fun provideAccountDao(database: YandexFinanceDatabase) = database.getAccountDao()

    @[Provides Singleton]
    internal fun provideTransactionsDao(database: YandexFinanceDatabase) = database.getTransactionsDao()

    @[Provides Singleton]
    internal fun provideCategoriesDao(database: YandexFinanceDatabase) = database.getCategoriesDao()
}