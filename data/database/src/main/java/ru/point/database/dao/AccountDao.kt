package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.api.model.AccountDto
import ru.point.database.model.PendingAccountUpdate

@Dao
interface AccountDao {

    @Query("SELECT * FROM account")
    fun getAllAccounts(): Flow<List<AccountDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAccount(account: AccountDto)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAccounts(accounts: List<AccountDto>) {
        accounts.forEach { insertAccount(it) }
    }

    @Query("DELETE FROM account")
    suspend fun clearAccountsTable()

    @Query("SELECT * FROM pending_account_update WHERE id = 0")
    fun getPendingAccountUpdate(): Flow<PendingAccountUpdate?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertPendingAccountUpdate(pending: PendingAccountUpdate)

    @Query("DELETE FROM pending_account_update")
    suspend fun clearPendingAccountUpdateTable()
}