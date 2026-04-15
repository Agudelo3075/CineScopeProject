package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.MovieEntity
import com.desarrollox.cinescopeproyect.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class MovieDetailUiState(
    val isLoading: Boolean = false,
    val movie: MovieEntity? = null,
    val isFavorite: Boolean = false,
    val isInMyList: Boolean = false,
    val isInHistory: Boolean = false,
    val userRating: Int = 0,
    val userComment: String = "",
    val error: String? = null,
    val actionMessage: String? = null
)

class MovieDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContentRepository(application)

    private val _uiState = MutableStateFlow(MovieDetailUiState())
    val uiState: StateFlow<MovieDetailUiState> = _uiState.asStateFlow()

    fun loadMovie(movie: MovieEntity) {
        viewModelScope.launch {
            val dbMovie = repository.getMovieByTitle(movie.title) ?: movie
            _uiState.value = _uiState.value.copy(
                movie = dbMovie,
                isLoading = true
            )
            
            combine(
                repository.isFavorite(dbMovie.id),
                repository.isInMyList(dbMovie.id),
                repository.isInHistory(dbMovie.id),
                repository.getRating(dbMovie.id)
            ) { isFavorite, isInMyList, isInHistory, rating ->
                _uiState.value.copy(
                    isFavorite = isFavorite,
                    isInMyList = isInMyList,
                    isInHistory = isInHistory,
                    userRating = rating?.stars ?: 0,
                    userComment = rating?.comment ?: "",
                    isLoading = false
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun toggleFavorite() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            if (_uiState.value.isFavorite) {
                repository.removeFromFavorites(movie.id)
                _uiState.value = _uiState.value.copy(
                    isFavorite = false,
                    actionMessage = "Removed from favorites"
                )
            } else {
                repository.addToFavorites(movie)
                _uiState.value = _uiState.value.copy(
                    isFavorite = true,
                    actionMessage = "Added to favorites"
                )
            }
        }
    }

    fun toggleMyList() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            if (_uiState.value.isInMyList) {
                repository.removeFromMyList(movie.id)
                _uiState.value = _uiState.value.copy(
                    isInMyList = false,
                    actionMessage = "Removed from My List"
                )
            } else {
                repository.addToMyList(movie)
                _uiState.value = _uiState.value.copy(
                    isInMyList = true,
                    actionMessage = "Added to My List"
                )
            }
        }
    }

    fun markAsWatched() {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            repository.addToHistory(movie)
            _uiState.value = _uiState.value.copy(
                isInHistory = true,
                actionMessage = "Marked as watched"
            )
        }
    }

    fun updateProgress(progress: Float) {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            repository.updateProgress(movie.id, progress)
            if (progress >= 1f) {
                repository.markAsCompleted(movie.id)
            }
        }
    }

    fun rateMovie(stars: Int, comment: String) {
        val movie = _uiState.value.movie ?: return
        viewModelScope.launch {
            repository.rateMovie(movie.id, movie.title, stars, comment)
            _uiState.value = _uiState.value.copy(
                userRating = stars,
                userComment = comment,
                actionMessage = "Rating saved"
            )
        }
    }

    fun clearMessage() {
        _uiState.value = _uiState.value.copy(actionMessage = null)
    }
}
