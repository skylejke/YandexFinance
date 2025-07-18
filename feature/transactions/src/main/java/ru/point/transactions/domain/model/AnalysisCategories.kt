package ru.point.transactions.domain.model

internal data class AnalysisCategories(
    val id: Int,
    val title: String,
    val emojiIcon: String,
    val amount: String,
    val currency: String,
    val part: String,
)