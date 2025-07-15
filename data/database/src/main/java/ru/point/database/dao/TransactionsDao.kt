package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.api.model.TransactionResponseDto

@Dao
interface TransactionsDao {
    @Query(
        """
      SELECT * FROM transactions
       WHERE transaction_date BETWEEN :startDate AND :endDate
       ORDER BY transaction_date DESC
    """
    )
    fun getTransactionsInPeriod(
        startDate: String,
        endDate: String
    ): Flow<List<TransactionResponseDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllTransactions(items: List<TransactionResponseDto>)

    @Query(
        """
      DELETE FROM transactions
       WHERE transaction_date BETWEEN :startDate AND :endDate
    """
    )
    suspend fun clearPeriod(startDate: String, endDate: String)

    @Query("DELETE FROM transactions")
    suspend fun clearTransactionsTable()

    @Query("SELECT * FROM transactions WHERE id = :id")
    fun getTransactionById(id: Int): Flow<TransactionResponseDto?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(item: TransactionResponseDto)

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteTransactionById(id: Int)

    @Query(
        """
    UPDATE transactions
       SET account_name     = :name,
           account_balance  = :balance,
           account_currency = :currency
 """
    )
    suspend fun updateAllAccountsInTransactions(
        name: String,
        balance: String,
        currency: String
    )
}