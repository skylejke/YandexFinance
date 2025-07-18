package ru.point.database.repo

import ru.point.api.model.AccountDto
import ru.point.api.model.StateItemDto
import ru.point.api.model.TransactionResponseDto
import ru.point.dto.CategoryDto

interface LocalDatabaseRepository {

    suspend fun prefetchTransactions(): Result<List<TransactionResponseDto>>

    suspend fun prefetchAccounts(): Result<List<AccountDto>>

    suspend fun prefetchUsersCategories(): Result<List<StateItemDto>>

    suspend fun prefetchCategoriesByType(isIncome: Boolean): Result<List<CategoryDto>>
}