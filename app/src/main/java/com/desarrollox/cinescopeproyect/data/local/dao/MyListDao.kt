package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.MyListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MyListDao {
    @Query("SELECT * FROM my_list WHERE userId = :userId ORDER BY addedAt DESC")
    fun getAllItems(userId: Long): Flow<List<MyListEntity>>

    @Query("SELECT * FROM my_list WHERE userId = :userId AND type = :type ORDER BY addedAt DESC")
    fun getItemsByType(userId: Long, type: String): Flow<List<MyListEntity>>

    @Query("SELECT * FROM my_list WHERE userId = :userId AND movieId = :movieId LIMIT 1")
    suspend fun getItemByMovieId(userId: Long, movieId: Long): MyListEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM my_list WHERE userId = :userId AND movieId = :movieId)")
    fun isInMyList(userId: Long, movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: MyListEntity): Long

    @Delete
    suspend fun deleteItem(item: MyListEntity)

    @Query("DELETE FROM my_list WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteItemByMovieId(userId: Long, movieId: Long)

    @Query("SELECT COUNT(*) FROM my_list WHERE userId = :userId")
    fun getItemCount(userId: Long): Flow<Int>
}
