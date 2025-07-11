package ru.point.account.di.component

import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.account.ui.account.viewmodel.AccountViewModelFactory
import ru.point.utils.di.FeatureScope

@[FeatureScope Component(dependencies = [AccountDeps::class])]
internal interface AccountComponent {

    @Component.Builder
    interface Builder {
        fun deps(accountDeps: AccountDeps): Builder
        fun build(): AccountComponent
    }

    val accountViewModelFactory: AccountViewModelFactory
}