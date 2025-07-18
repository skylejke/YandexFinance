package ru.point.api.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.point.dto.CategoryDto

@Entity(tableName = "transactions")
data class TransactionResponseDto(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,

    @Embedded(prefix = "account_")
    val account: AccountStateDto,

    @Embedded(prefix = "category_")
    val category: CategoryDto,

    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "transaction_date") val transactionDate: String,
    @ColumnInfo(name = "comment") val comment: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: String,
)
