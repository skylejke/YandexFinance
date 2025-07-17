package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.database.model.PendingTransactionEntity

@Dao
interface PendingTransactionsDao {

    @Query("SELECT * FROM pending_transactions ORDER BY id ASC")
    fun getAllFlow(): Flow<List<PendingTransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(pending: PendingTransactionEntity)

    @Query("DELETE FROM pending_transactions WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("DELETE FROM pending_transactions")
    suspend fun clearAll()
}
