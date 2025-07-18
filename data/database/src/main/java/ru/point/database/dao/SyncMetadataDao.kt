package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.database.model.SyncMetadata

@Dao
interface SyncMetadataDao {
    @Query("SELECT last_sync FROM sync_metadata WHERE type = :type")
    fun getLastSync(type: String): Flow<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(metadata: SyncMetadata)
}
