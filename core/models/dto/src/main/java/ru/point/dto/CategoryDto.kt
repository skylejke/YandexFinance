package ru.point.dto

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.point.vo.CategoryVo


@Entity(tableName = "categories_dropdown")
data class CategoryDto(
    @PrimaryKey
    @ColumnInfo("id") val id: Int,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("emoji") val emoji: String,
    @ColumnInfo("is_income") val isIncome: Boolean
)

val CategoryDto.asCategoryVo
    get() = CategoryVo(
        id = id,
        name = name,
        emoji = emoji
    )