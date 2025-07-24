package ru.point.vo

import androidx.compose.runtime.Immutable

@Immutable
data class CategoryVo(
    val id: Int,
    val name: String,
    val emoji: String,
)