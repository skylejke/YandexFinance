package ru.point.database.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.point.api.model.AccountDto
import ru.point.api.model.StateItemDto
import ru.point.api.model.TransactionResponseDto
import ru.point.database.dao.AccountDao
import ru.point.database.dao.CategoriesDao
import ru.point.database.dao.PendingTransactionsDao
import ru.point.database.dao.TransactionsDao
import ru.point.database.model.PendingAccountUpdate
import ru.point.database.model.PendingTransactionEntity
import ru.point.dto.CategoryDto

const val DATABASE_NAME = "yandex_finance.db"

@Database(
    entities = [
        StateItemDto::class,
        AccountDto::class,
        TransactionResponseDto::class,
        CategoryDto::class,
        PendingAccountUpdate::class,
        PendingTransactionEntity::class,
    ],
    version = 1,
    exportSchema = false
)
abstract class YandexFinanceDatabase : RoomDatabase() {

    abstract fun getAccountDao(): AccountDao
    abstract fun getCategoriesDao(): CategoriesDao
    abstract fun getTransactionsDao(): TransactionsDao
    abstract fun getPendingTransactionsDao(): PendingTransactionsDao

    companion object {
        fun getDataBase(context: Context) =
            Room.databaseBuilder(context, YandexFinanceDatabase::class.java, DATABASE_NAME).build()
    }
}