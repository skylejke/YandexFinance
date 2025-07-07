package ru.point.transactions.di.deps

interface TransactionDepsProvider {

    val transactionDeps: TransactionDeps

    companion object : TransactionDepsProvider by TransactionDepsStore
}