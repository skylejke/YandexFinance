package ru.point.api.model

data class StateItemDto(
    val categoryId: Int,
    val categoryName: String,
    val emoji: String,
    val amount: String,
)