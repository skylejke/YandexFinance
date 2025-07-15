package ru.point.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pending_account_update")
data class PendingAccountUpdate(
    @PrimaryKey val id: Int = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("balance") val balance: String,
    @ColumnInfo("currency") val currency: String
)