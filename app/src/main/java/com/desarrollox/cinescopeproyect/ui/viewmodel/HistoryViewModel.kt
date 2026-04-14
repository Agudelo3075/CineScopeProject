package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.WatchHistoryEntity
import com.desarrollox.cinescopeproyect.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HistoryUiState(
    val isLoading: Boolean = true,
    val selectedTab: Int = 0,
    val movies: List<WatchHistoryEntity> = emptyList(),
    val series: List<WatchHistoryEntity> = emptyList(),
    val documentaries: List<WatchHistoryEntity> = emptyList(),
    val myList: List<WatchHistoryEntity> = emptyList(),
    val groupedHistory: Map<String, List<WatchHistoryEntity>> = emptyMap(),
    val error: String? = null
)

class HistoryViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContentRepository(application)

    private val _uiState = MutableStateFlow(HistoryUiState())
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            repository.getAllHistory()
                .collect { history ->
                    val movies = history.filter { it.type == "Movie" }
                    val series = history.filter { it.type == "Series" }
                    val documentaries = history.filter { it.type == "Documentary" }

                    val grouped = history.groupBy { item ->
                        val now = System.currentTimeMillis()
                        val diff = now - item.watchedAt
                        when {
                            diff < 24 * 60 * 60 * 1000 -> "TODAY"
                            diff < 48 * 60 * 60 * 1000 -> "YESTERDAY"
                            diff < 7 * 24 * 60 * 60 * 1000 -> "THIS WEEK"
                            else -> "EARLIER"
                        }
                    }

                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        movies = movies,
                        series = series,
                        documentaries = documentaries,
                        groupedHistory = grouped
                    )
                }
        }
    }

    fun selectTab(index: Int) {
        _uiState.value = _uiState.value.copy(selectedTab = index)
    }

    fun updateProgress(movieId: Long, progress: Float) {
        viewModelScope.launch {
            repository.updateProgress(movieId, progress)
        }
    }

    fun removeFromHistory(movieId: Long) {
        viewModelScope.launch {
            repository.removeFromHistory(movieId)
        }
    }
}
