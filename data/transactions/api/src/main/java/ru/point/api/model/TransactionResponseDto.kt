package ru.point.api.model

import ru.point.dto.CategoryDto

data class TransactionResponseDto(
    val id: Int,
    val account: AccountStateDto,
    val category: CategoryDto,
    val amount: String,
    val transactionDate: String,
    val comment: String? = null,
    val createdAt: String,
    val updatedAt: String
)
