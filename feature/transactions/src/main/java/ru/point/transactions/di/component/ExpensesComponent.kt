package ru.point.transactions.di.component

import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.expenses.viewmodel.ExpensesViewModelFactory

@Component(dependencies = [TransactionDeps::class])
internal interface ExpensesComponent {

    @Component.Builder
    interface Builder {

        fun deps(transactionDeps: TransactionDeps): Builder
        fun build(): ExpensesComponent
    }

    val expensesViewModelFactory: ExpensesViewModelFactory
}