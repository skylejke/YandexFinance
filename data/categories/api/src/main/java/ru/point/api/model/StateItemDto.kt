package ru.point.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.point.vo.CategoryVo

@Entity(tableName = "users_categories")
data class StateItemDto(
    @PrimaryKey
    @ColumnInfo("category_id") val categoryId: Int,
    @ColumnInfo("category_name") val categoryName: String,
    @ColumnInfo("emoji") val emoji: String,
    @ColumnInfo("amount") val amount: String,
)

val StateItemDto.asCategoryVo
    get() = CategoryVo(
        id = categoryId,
        name = categoryName,
        emoji = emoji
    )