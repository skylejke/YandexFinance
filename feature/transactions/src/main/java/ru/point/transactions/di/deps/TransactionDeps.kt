package ru.point.transactions.di.deps

import ru.point.api.repository.CategoriesRepository
import ru.point.api.repository.TransactionsRepository

interface TransactionDeps {

    val transactionsRepository: TransactionsRepository

    val categoriesRepository: CategoriesRepository
}