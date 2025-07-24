package ru.point.impl.di

import android.content.Context
import dagger.Module
import dagger.Provides
import ru.point.api.repository.SettingsRepository
import ru.point.impl.repository.SettingsRepositoryImpl
import javax.inject.Singleton

@Module
class SettingsDataModule {

    @[Provides Singleton]
    internal fun provideSettingsRepository(context: Context): SettingsRepository =
        SettingsRepositoryImpl(context = context)
}