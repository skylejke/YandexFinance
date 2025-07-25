package ru.point.settings.di.component

import dagger.Component
import ru.point.settings.di.deps.SettingsDeps
import ru.point.settings.ui.appinfo.viewmodel.AppInfoViewModelFactory
import ru.point.utils.di.FeatureScope

@[FeatureScope Component(dependencies = [SettingsDeps::class])]
internal interface AppInfoComponent {

    @Component.Builder
    interface Builder {
        fun deps(settingsDeps: SettingsDeps): Builder
        fun build(): AppInfoComponent
    }

    val appInfoViewModelFactory: AppInfoViewModelFactory
}