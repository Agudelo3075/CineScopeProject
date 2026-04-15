package com.desarrollox.cinescopeproyect.data.repository

import android.content.Context
import com.desarrollox.cinescopeproyect.data.local.DatabaseProvider
import com.desarrollox.cinescopeproyect.data.local.entity.*
import kotlinx.coroutines.flow.Flow

class ContentRepository(context: Context) {
    private val favoriteDao = DatabaseProvider.getFavoriteDao(context)
    private val watchHistoryDao = DatabaseProvider.getWatchHistoryDao(context)
    private val myListDao = DatabaseProvider.getMyListDao(context)
    private val ratingDao = DatabaseProvider.getRatingDao(context)
    private val movieDao = DatabaseProvider.getMovieDao(context)

    private val prefs = context.getSharedPreferences("cinescope_prefs", Context.MODE_PRIVATE)
    private fun getUserId(): Long = prefs.getLong("user_id", -1)

    fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites(getUserId())
    fun getFavoriteMovies(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteMovies(getUserId())
    fun getFavoriteSeries(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteSeries(getUserId())
    fun isFavorite(movieId: Long): Flow<Boolean> = favoriteDao.isFavorite(getUserId(), movieId)
    
    suspend fun addToFavorites(movie: MovieEntity, type: String = "Movie") {
        val favorite = FavoriteEntity(
            userId = getUserId(),
            movieId = movie.id,
            title = movie.title,
            year = movie.year,
            genre = movie.genre,
            rating = movie.rating,
            type = type,
            color1 = movie.color1,
            color2 = movie.color2
        )
        favoriteDao.insertFavorite(favorite)
    }

    suspend fun removeFromFavorites(movieId: Long) {
        favoriteDao.deleteFavoriteByMovieId(getUserId(), movieId)
    }

    fun getAllHistory(): Flow<List<WatchHistoryEntity>> = watchHistoryDao.getAllHistory(getUserId())
    fun getHistoryByType(type: String): Flow<List<WatchHistoryEntity>> = watchHistoryDao.getHistoryByType(getUserId(), type)
    fun isInHistory(movieId: Long): Flow<Boolean> = watchHistoryDao.isInHistory(getUserId(), movieId)
    
    suspend fun addToHistory(movie: MovieEntity, type: String = "Movie") {
        val existing = watchHistoryDao.getHistoryByMovieId(getUserId(), movie.id)
        if (existing == null) {
            val history = WatchHistoryEntity(
                userId = getUserId(),
                movieId = movie.id,
                title = movie.title,
                year = movie.year,
                genre = movie.genre,
                type = type,
                color1 = movie.color1,
                color2 = movie.color2,
                isCompleted = true
            )
            watchHistoryDao.insertHistory(history)
        }
    }

    suspend fun updateProgress(movieId: Long, progress: Float) {
        watchHistoryDao.updateProgress(getUserId(), movieId, progress)
    }

    suspend fun markAsCompleted(movieId: Long) {
        watchHistoryDao.updateCompleted(getUserId(), movieId, true)
    }

    suspend fun removeFromHistory(movieId: Long) {
        watchHistoryDao.deleteHistoryByMovieId(getUserId(), movieId)
    }

    fun getMyList(): Flow<List<MyListEntity>> = myListDao.getAllItems(getUserId())
    fun isInMyList(movieId: Long): Flow<Boolean> = myListDao.isInMyList(getUserId(), movieId)
    
    suspend fun addToMyList(movie: MovieEntity, matchPercentage: Int = 95, type: String = "Movie") {
        val item = MyListEntity(
            userId = getUserId(),
            movieId = movie.id,
            title = movie.title,
            matchPercentage = matchPercentage,
            genres = movie.genre,
            type = type,
            color1 = movie.color1,
            color2 = movie.color2
        )
        myListDao.insertItem(item)
    }

    suspend fun removeFromMyList(movieId: Long) {
        myListDao.deleteItemByMovieId(getUserId(), movieId)
    }

    fun getRating(movieId: Long): Flow<RatingEntity?> = ratingDao.getRatingByMovieIdFlow(movieId, getUserId())
    
    suspend fun rateMovie(movieId: Long, movieTitle: String, stars: Int, comment: String = "") {
        val rating = RatingEntity(
            userId = getUserId(),
            movieId = movieId,
            movieTitle = movieTitle,
            stars = stars,
            comment = comment
        )
        ratingDao.insertRating(rating)
    }

    suspend fun removeRating(movieId: Long) {
        ratingDao.deleteRatingByMovieId(movieId, getUserId())
    }

    suspend fun getMovieByTitle(title: String) = movieDao.getMovieByTitle(title)
}
