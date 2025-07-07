package ru.point.transactions.di.component

import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.incomes.viewmodel.IncomesViewModelFactory

@Component(dependencies = [TransactionDeps::class])
internal interface IncomesComponent {

    @Component.Builder
    interface Builder {

        fun deps(transactionDeps: TransactionDeps): Builder
        fun build(): IncomesComponent
    }

    val incomesViewModelFactory: IncomesViewModelFactory
}