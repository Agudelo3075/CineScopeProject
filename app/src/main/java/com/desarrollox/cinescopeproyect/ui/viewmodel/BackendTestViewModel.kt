package com.desarrollox.cinescopeproyect.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.remote.BackendRetrofitClient
import com.desarrollox.cinescopeproyect.data.remote.model.MovieBackend
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class BackendTestUiState(
    val movies: List<MovieBackend> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val successMessage: String? = null
)

class BackendTestViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BackendTestUiState())
    val uiState: StateFlow<BackendTestUiState> = _uiState.asStateFlow()

    private val api = BackendRetrofitClient.apiService

    init {
        getMovies()
    }

    fun getMovies() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                val response = api.getMovies()
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(
                        movies = response.body() ?: emptyList(),
                        isLoading = false,
                        successMessage = "Loaded ${response.body()?.size ?: 0} movies"
                    )
                } else {
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        error = "Error: ${response.code()}"
                    )
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun createMovie(title: String, director: String, year: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                val newMovie = MovieBackend(title = title, director = director, year = year)
                val response = api.createMovie(newMovie)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(successMessage = "Movie created successfully!")
                    getMovies()
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "Error creating: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun updateMovie(id: Int, title: String, director: String, year: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                val updatedMovie = MovieBackend(title = title, director = director, year = year)
                val response = api.updateMovie(id, updatedMovie)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(successMessage = "Movie updated successfully!")
                    getMovies()
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "Error updating: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }

    fun deleteMovie(id: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, error = null, successMessage = null)
            try {
                val response = api.deleteMovie(id)
                if (response.isSuccessful) {
                    _uiState.value = _uiState.value.copy(successMessage = "Movie deleted successfully!")
                    getMovies()
                } else {
                    _uiState.value = _uiState.value.copy(isLoading = false, error = "Error deleting: ${response.code()}")
                }
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false, error = e.message)
            }
        }
    }
}
