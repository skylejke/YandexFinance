package ru.point.api.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "account")
data class AccountDto(
    @PrimaryKey
    @ColumnInfo("name") val name: String,
    @ColumnInfo("balance") val balance: String,
    @ColumnInfo("currency") val currency: String,
)