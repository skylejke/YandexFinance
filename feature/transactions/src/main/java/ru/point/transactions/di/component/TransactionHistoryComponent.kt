package ru.point.transactions.di.component

import dagger.BindsInstance
import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.history.viewmodel.TransactionHistoryViewModelFactory
import javax.inject.Named

@Component(dependencies = [TransactionDeps::class])
internal interface TransactionHistoryComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun isIncome(@Named("isIncome") isIncome: Boolean): Builder
        fun deps(transactionDeps: TransactionDeps): Builder
        fun build(): TransactionHistoryComponent
    }

    val transactionHistoryViewModelFactory: TransactionHistoryViewModelFactory
}