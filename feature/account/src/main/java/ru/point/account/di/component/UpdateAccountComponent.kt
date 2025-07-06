package ru.point.account.di.component

import dagger.Component
import ru.point.account.di.deps.AccountDeps
import ru.point.account.ui.update.viewmodel.UpdateAccountViewModelFactory

@Component(dependencies = [AccountDeps::class])
interface UpdateAccountComponent {

    @Component.Builder
    interface Builder {
        fun deps(accountDeps: AccountDeps): Builder
        fun build(): UpdateAccountComponent
    }

    val updateAccountViewModelFactory: UpdateAccountViewModelFactory
}