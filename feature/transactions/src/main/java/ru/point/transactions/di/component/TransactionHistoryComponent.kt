package ru.point.transactions.di.component

import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.history.viewmodel.TransactionHistoryViewModelFactory

@Component(dependencies = [TransactionDeps::class])
internal interface TransactionHistoryComponent {

    @Component.Builder
    interface Builder {

        fun deps(transactionDeps: TransactionDeps): Builder
        fun build(): TransactionHistoryComponent
    }

    val transactionHistoryViewModelFactory: TransactionHistoryViewModelFactory
}