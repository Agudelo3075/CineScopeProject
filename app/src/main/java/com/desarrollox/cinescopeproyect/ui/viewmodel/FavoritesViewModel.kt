package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.FavoriteEntity
import com.desarrollox.cinescopeproyect.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val selectedTab: Int = 0,
    val movies: List<FavoriteEntity> = emptyList(),
    val series: List<FavoriteEntity> = emptyList(),
    val error: String? = null
)

class FavoritesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContentRepository(application)

    private val _uiState = MutableStateFlow(FavoritesUiState())
    val uiState: StateFlow<FavoritesUiState> = _uiState.asStateFlow()

    init {
        loadFavorites()
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            launch {
                repository.getFavoriteMovies()
                    .collect { movies ->
                        _uiState.value = _uiState.value.copy(movies = movies)
                    }
            }
            launch {
                repository.getFavoriteSeries()
                    .collect { series ->
                        _uiState.value = _uiState.value.copy(series = series, isLoading = false)
                    }
            }
        }
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun removeFavorite(movieId: Long) {
        viewModelScope.launch {
            repository.removeFromFavorites(movieId)
        }
    }
}
