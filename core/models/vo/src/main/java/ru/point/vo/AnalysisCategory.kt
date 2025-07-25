package ru.point.vo

import androidx.compose.runtime.Immutable

@Immutable
data class AnalysisCategory(
    val id: Int,
    val title: String,
    val emojiIcon: String,
    val amount: String,
    val currency: String,
    val part: String,
)