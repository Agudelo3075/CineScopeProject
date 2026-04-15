package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.WatchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchHistoryDao {
    @Query("SELECT * FROM watch_history WHERE userId = :userId ORDER BY watchedAt DESC")
    fun getAllHistory(userId: Long): Flow<List<WatchHistoryEntity>>

    @Query("SELECT * FROM watch_history WHERE userId = :userId AND type = :type ORDER BY watchedAt DESC")
    fun getHistoryByType(userId: Long, type: String): Flow<List<WatchHistoryEntity>>

    @Query("SELECT * FROM watch_history WHERE userId = :userId AND movieId = :movieId LIMIT 1")
    suspend fun getHistoryByMovieId(userId: Long, movieId: Long): WatchHistoryEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM watch_history WHERE userId = :userId AND movieId = :movieId)")
    fun isInHistory(userId: Long, movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WatchHistoryEntity): Long

    @Update
    suspend fun updateHistory(history: WatchHistoryEntity)

    @Delete
    suspend fun deleteHistory(history: WatchHistoryEntity)

    @Query("DELETE FROM watch_history WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteHistoryByMovieId(userId: Long, movieId: Long)

    @Query("SELECT COUNT(*) FROM watch_history WHERE userId = :userId")
    fun getHistoryCount(userId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM watch_history WHERE userId = :userId AND isCompleted = 1")
    fun getCompletedCount(userId: Long): Flow<Int>

    @Query("UPDATE watch_history SET progress = :progress WHERE userId = :userId AND movieId = :movieId")
    suspend fun updateProgress(userId: Long, movieId: Long, progress: Float)

    @Query("UPDATE watch_history SET isCompleted = :completed WHERE userId = :userId AND movieId = :movieId")
    suspend fun updateCompleted(userId: Long, movieId: Long, completed: Boolean)
}
