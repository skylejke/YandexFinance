package ru.point.yandexfinance.di.component

import androidx.lifecycle.ViewModelProvider
import dagger.Component
import ru.point.impl.di.NetworkModule
import ru.point.impl.di.RepositoryModule
import ru.point.yandexfinance.di.modules.ViewModelModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
    ],
)
interface AppComponent {

    fun viewModelFactoryProvider(): ViewModelProvider.Factory
}