package ru.point.transactions.di.component

import dagger.BindsInstance
import dagger.Component
import ru.point.transactions.di.deps.TransactionDeps
import ru.point.transactions.ui.editor.viewmodel.TransactionEditorViewModelFactory
import ru.point.utils.di.FeatureScope
import javax.inject.Named

@[FeatureScope Component(dependencies = [TransactionDeps::class])]
internal interface TransactionEditorComponent {

    @Component.Builder
    interface Builder {

        fun deps(transactionDeps: TransactionDeps): Builder

        @BindsInstance
        fun transactionId(@Named("transactionId") transactionId: Int?): Builder

        @BindsInstance
        fun isIncome(@Named("isIncome") isIncome: Boolean): Builder

        fun build(): TransactionEditorComponent
    }

    val transactionEditorViewModelFactory: TransactionEditorViewModelFactory
}