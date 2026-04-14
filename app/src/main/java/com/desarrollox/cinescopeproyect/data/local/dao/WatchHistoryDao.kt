package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.WatchHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchHistoryDao {
    @Query("SELECT * FROM watch_history ORDER BY watchedAt DESC")
    fun getAllHistory(): Flow<List<WatchHistoryEntity>>

    @Query("SELECT * FROM watch_history WHERE type = :type ORDER BY watchedAt DESC")
    fun getHistoryByType(type: String): Flow<List<WatchHistoryEntity>>

    @Query("SELECT * FROM watch_history WHERE movieId = :movieId LIMIT 1")
    suspend fun getHistoryByMovieId(movieId: Long): WatchHistoryEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM watch_history WHERE movieId = :movieId)")
    fun isInHistory(movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: WatchHistoryEntity): Long

    @Update
    suspend fun updateHistory(history: WatchHistoryEntity)

    @Delete
    suspend fun deleteHistory(history: WatchHistoryEntity)

    @Query("DELETE FROM watch_history WHERE movieId = :movieId")
    suspend fun deleteHistoryByMovieId(movieId: Long)

    @Query("SELECT COUNT(*) FROM watch_history")
    fun getHistoryCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM watch_history WHERE isCompleted = 1")
    fun getCompletedCount(): Flow<Int>

    @Query("UPDATE watch_history SET progress = :progress WHERE movieId = :movieId")
    suspend fun updateProgress(movieId: Long, progress: Float)

    @Query("UPDATE watch_history SET isCompleted = :completed WHERE movieId = :movieId")
    suspend fun updateCompleted(movieId: Long, completed: Boolean)
}
