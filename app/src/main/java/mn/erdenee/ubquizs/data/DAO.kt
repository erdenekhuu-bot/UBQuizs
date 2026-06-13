package mn.erdenee.ubquizs.data

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {
    @Upsert
    suspend fun insertCategory(categories: List<CategoryEntity>)
    @Query("SELECT * FROM categories ORDER BY id ASC")
    fun getAllCategories(): Flow<List<CategoryEntity>>
    @Query("DELETE FROM categories")
    suspend fun clearAll()
}