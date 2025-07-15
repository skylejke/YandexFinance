package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.api.model.StateItemDto
import ru.point.dto.CategoryDto

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM categories")
    fun getAllCategories(): Flow<List<StateItemDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategories(items: List<StateItemDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(item: StateItemDto)

    @Delete
    suspend fun deleteCategory(item: StateItemDto)

    @Query("DELETE FROM categories")
    suspend fun clearCategoriesTable()

    @Query("SELECT * FROM categories_dropdown WHERE is_income = :isIncome")
    fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCategoriesByType(items: List<CategoryDto>)

    @Query("DELETE FROM categories_dropdown WHERE is_income = :isIncome")
    suspend fun clearCategoriesByType(isIncome: Boolean)
}