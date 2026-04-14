package com.desarrollox.cinescopeproyect.data.local.dao

import androidx.room.*
import com.desarrollox.cinescopeproyect.data.local.entity.RatingEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {
    @Query("SELECT * FROM ratings WHERE movieId = :movieId LIMIT 1")
    suspend fun getRatingByMovieId(movieId: Long): RatingEntity?

    @Query("SELECT * FROM ratings WHERE movieId = :movieId")
    fun getRatingByMovieIdFlow(movieId: Long): Flow<RatingEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: RatingEntity): Long

    @Update
    suspend fun updateRating(rating: RatingEntity)

    @Delete
    suspend fun deleteRating(rating: RatingEntity)

    @Query("DELETE FROM ratings WHERE movieId = :movieId")
    suspend fun deleteRatingByMovieId(movieId: Long)

    @Query("SELECT AVG(stars) FROM ratings")
    fun getAverageRating(): Flow<Float?>

    @Query("SELECT COUNT(*) FROM ratings")
    fun getRatingCount(): Flow<Int>
}
