package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.MovieEntity
import com.desarrollox.cinescopeproyect.data.repository.MovieRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class SearchUiState(
    val query: String = "",
    val selectedChip: String = "All",
    val results: List<MovieEntity> = emptyList(),
    val isSearching: Boolean = false,
    val hasSearched: Boolean = false
)

@OptIn(FlowPreview::class)
class SearchViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = MovieRepository(application)

    private val _query = MutableStateFlow("")
    private val _selectedChip = MutableStateFlow("All")

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initializeMovies()
        }

        viewModelScope.launch {
            combine(_query.debounce(300), _selectedChip) { query, chip ->
                Pair(query, chip)
            }.collectLatest { (query, chip) ->
                _uiState.value = _uiState.value.copy(
                    query = query,
                    selectedChip = chip,
                    isSearching = query.isNotBlank()
                )

                if (query.isNotBlank()) {
                    repository.searchMovies(query)
                        .collect { movies ->
                            val filtered = if (chip == "All") {
                                movies
                            } else {
                                movies.filter { it.genre.equals(chip, ignoreCase = true) }
                            }
                            _uiState.value = _uiState.value.copy(
                                results = filtered,
                                isSearching = false,
                                hasSearched = true
                            )
                        }
                } else {
                    _uiState.value = _uiState.value.copy(
                        results = emptyList(),
                        hasSearched = false
                    )
                }
            }
        }
    }

    fun updateQuery(newQuery: String) {
        _query.value = newQuery
    }

    fun selectChip(chip: String) {
        _selectedChip.value = chip
    }
}
