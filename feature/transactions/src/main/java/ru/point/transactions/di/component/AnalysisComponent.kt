package ru.point.transactions.di.component

import dagger.BindsInstance
import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.analysis.viewmodel.AnalysisViewModelFactory
import ru.point.utils.di.FeatureScope
import javax.inject.Named

@[FeatureScope Component(dependencies = [TransactionDeps::class])]
internal interface AnalysisComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun isIncome(@Named("isIncome") isIncome: Boolean): Builder
        fun deps(transactionDeps: TransactionDeps): Builder
        fun build(): AnalysisComponent
    }

    val analysisViewModelFactory: AnalysisViewModelFactory
}