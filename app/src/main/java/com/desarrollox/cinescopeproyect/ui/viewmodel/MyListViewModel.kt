package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.MyListEntity
import com.desarrollox.cinescopeproyect.data.repository.ContentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MyListUiState(
    val isLoading: Boolean = true,
    val items: List<MyListEntity> = emptyList(),
    val itemCount: Int = 0,
    val error: String? = null
)

class MyListViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = ContentRepository(application)

    private val _uiState = MutableStateFlow(MyListUiState())
    val uiState: StateFlow<MyListUiState> = _uiState.asStateFlow()

    init {
        loadMyList()
    }

    private fun loadMyList() {
        viewModelScope.launch {
            repository.getMyList()
                .collect { items ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        items = items,
                        itemCount = items.size
                    )
                }
        }
    }

    fun removeFromList(movieId: Long) {
        viewModelScope.launch {
            repository.removeFromMyList(movieId)
        }
    }
}
