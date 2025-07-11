package ru.point.transactions.di.deps

import kotlin.properties.Delegates.notNull

object TransactionDepsStore: TransactionDepsProvider {

    override var transactionDeps: TransactionDeps by notNull()
}