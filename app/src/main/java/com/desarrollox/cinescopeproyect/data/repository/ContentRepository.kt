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

    fun getAllFavorites(): Flow<List<FavoriteEntity>> = favoriteDao.getAllFavorites()
    fun getFavoriteMovies(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteMovies()
    fun getFavoriteSeries(): Flow<List<FavoriteEntity>> = favoriteDao.getFavoriteSeries()
    fun isFavorite(movieId: Long): Flow<Boolean> = favoriteDao.isFavorite(movieId)
    
    suspend fun addToFavorites(movie: MovieEntity, type: String = "Movie") {
        val favorite = FavoriteEntity(
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
        favoriteDao.deleteFavoriteByMovieId(movieId)
    }

    fun getAllHistory(): Flow<List<WatchHistoryEntity>> = watchHistoryDao.getAllHistory()
    fun getHistoryByType(type: String): Flow<List<WatchHistoryEntity>> = watchHistoryDao.getHistoryByType(type)
    fun isInHistory(movieId: Long): Flow<Boolean> = watchHistoryDao.isInHistory(movieId)
    
    suspend fun addToHistory(movie: MovieEntity, type: String = "Movie") {
        val existing = watchHistoryDao.getHistoryByMovieId(movie.id)
        if (existing == null) {
            val history = WatchHistoryEntity(
                movieId = movie.id,
                title = movie.title,
                year = movie.year,
                genre = movie.genre,
                type = type,
                color1 = movie.color1,
                color2 = movie.color2
            )
            watchHistoryDao.insertHistory(history)
        }
    }

    suspend fun updateProgress(movieId: Long, progress: Float) {
        watchHistoryDao.updateProgress(movieId, progress)
    }

    suspend fun markAsCompleted(movieId: Long) {
        watchHistoryDao.updateCompleted(movieId, true)
    }

    suspend fun removeFromHistory(movieId: Long) {
        watchHistoryDao.deleteHistoryByMovieId(movieId)
    }

    fun getMyList(): Flow<List<MyListEntity>> = myListDao.getAllItems()
    fun isInMyList(movieId: Long): Flow<Boolean> = myListDao.isInMyList(movieId)
    
    suspend fun addToMyList(movie: MovieEntity, matchPercentage: Int = 95, type: String = "Movie") {
        val item = MyListEntity(
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
        myListDao.deleteItemByMovieId(movieId)
    }

    fun getRating(movieId: Long): Flow<RatingEntity?> = ratingDao.getRatingByMovieIdFlow(movieId)
    
    suspend fun rateMovie(movieId: Long, movieTitle: String, stars: Int, comment: String = "") {
        val rating = RatingEntity(
            movieId = movieId,
            movieTitle = movieTitle,
            stars = stars,
            comment = comment
        )
        ratingDao.insertRating(rating)
    }

    suspend fun removeRating(movieId: Long) {
        ratingDao.deleteRatingByMovieId(movieId)
    }
}
