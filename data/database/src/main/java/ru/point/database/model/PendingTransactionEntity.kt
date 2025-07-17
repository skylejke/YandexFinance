package ru.point.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_transactions")
data class PendingTransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("transaction_id") val transactionId: Int,
    @ColumnInfo("category_id") val categoryId: Int?,
    @ColumnInfo("amount") val amount: String?,
    @ColumnInfo("transaction_date") val transactionDate: String?,
    @ColumnInfo("comment") val comment: String?,
    @ColumnInfo("operation") val operation: PendingTransactionOperation
)

enum class PendingTransactionOperation {
    CREATE, UPDATE, DELETE
}
