package ru.point.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.point.api.model.StateItemDto
import ru.point.dto.CategoryDto

@Dao
interface CategoriesDao {

    @Query("SELECT * FROM users_categories")
    fun getUsersCategories(): Flow<List<StateItemDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersCategories(items: List<StateItemDto>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersCategory(item: StateItemDto)

    @Query("DELETE FROM users_categories")
    suspend fun clearUsersCategories()

    @Query("SELECT * FROM categories WHERE is_income = :isIncome")
    fun getCategoriesByType(isIncome: Boolean): Flow<List<CategoryDto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoriesByType(items: List<CategoryDto>)

    @Query("DELETE FROM categories WHERE is_income = :isIncome")
    suspend fun clearCategoriesByType(isIncome: Boolean)

    @Query("SELECT * FROM categories WHERE id = :categoryId")
    fun getCategoryById(categoryId: Int): Flow<CategoryDto>
}