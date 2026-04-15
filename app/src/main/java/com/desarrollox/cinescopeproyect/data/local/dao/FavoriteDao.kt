package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorites WHERE userId = :userId ORDER BY addedAt DESC")
    fun getAllFavorites(userId: Long): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE userId = :userId AND type = :type ORDER BY addedAt DESC")
    fun getFavoritesByType(userId: Long, type: String): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE userId = :userId AND type = 'Movie' ORDER BY addedAt DESC")
    fun getFavoriteMovies(userId: Long): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE userId = :userId AND type = 'Series' ORDER BY addedAt DESC")
    fun getFavoriteSeries(userId: Long): Flow<List<FavoriteEntity>>

    @Query("SELECT * FROM favorites WHERE userId = :userId AND movieId = :movieId LIMIT 1")
    suspend fun getFavoriteByMovieId(userId: Long, movieId: Long): FavoriteEntity?

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE userId = :userId AND movieId = :movieId)")
    fun isFavorite(userId: Long, movieId: Long): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteEntity): Long

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)

    @Query("DELETE FROM favorites WHERE userId = :userId AND movieId = :movieId")
    suspend fun deleteFavoriteByMovieId(userId: Long, movieId: Long)

    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId")
    fun getFavoriteCount(userId: Long): Flow<Int>

    @Query("SELECT COUNT(*) FROM favorites WHERE userId = :userId AND type = :type")
    fun getFavoriteCountByType(userId: Long, type: String): Flow<Int>
}
