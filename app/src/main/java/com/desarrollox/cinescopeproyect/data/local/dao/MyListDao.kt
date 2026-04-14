package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.MyListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {
    @Query("SELECT * FROM my_list ORDER BY addedAt DESC")
    fun getAllItems(): Flow<List<MyListEntity>>

    @Query("SELECT * FROM my_list WHERE type = :type ORDER BY addedAt DESC")
    fun getItemsByType(type: String): Flow<List<MyListEntity>>

    @Query("SELECT * FROM my_list WHERE movieId = :movieId LIMIT 1")
    suspend fun getItemByMovieId(movieId: Long): MyListEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM my_list WHERE movieId = :movieId)")
    fun isInMyList(movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: MyListEntity): Long

    @Delete
    suspend fun deleteItem(item: MyListEntity)

    @Query("DELETE FROM my_list WHERE movieId = :movieId")
    suspend fun deleteItemByMovieId(movieId: Long)

    @Query("SELECT COUNT(*) FROM my_list")
    fun getItemCount(): Flow<Int>
}
