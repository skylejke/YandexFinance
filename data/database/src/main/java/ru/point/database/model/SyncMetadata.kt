package ru.point.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sync_metadata")
data class SyncMetadata(
    @PrimaryKey val type: String,
    @ColumnInfo(name = "last_sync") val lastSync: Long
)
