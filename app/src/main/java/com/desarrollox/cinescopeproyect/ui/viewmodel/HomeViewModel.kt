package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.MovieEntity
import com.desarrollox.cinescopeproyect.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = true,
    val popularMovies: List<MovieEntity> = emptyList(),
    val topRatedMovies: List<MovieEntity> = emptyList(),
    val newReleases: List<MovieEntity> = emptyList(),
    val allMovies: List<MovieEntity> = emptyList(),
    val error: String? = null
)

class HomeViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            repository.initializeMovies()
            
            launch {
                repository.getPopularMovies()
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { movies ->
                        _uiState.value = _uiState.value.copy(popularMovies = movies)
                    }
            }

            launch {
                repository.getTopRatedMovies()
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { movies ->
                        _uiState.value = _uiState.value.copy(topRatedMovies = movies)
                    }
            }

            launch {
                repository.getNewReleaseMovies()
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { movies ->
                        _uiState.value = _uiState.value.copy(newReleases = movies, isLoading = false)
                    }
            }

            launch {
                repository.getAllMovies()
                    .catch { e -> _uiState.value = _uiState.value.copy(error = e.message) }
                    .collect { movies ->
                        _uiState.value = _uiState.value.copy(allMovies = movies)
                    }
            }
        }
    }

    fun getMovieByTitle(title: String): MovieEntity? {
        return _uiState.value.allMovies.find { it.title == title }
    }

    fun getMovieById(id: Long): MovieEntity? {
        return _uiState.value.allMovies.find { it.id == id }
    }
}
