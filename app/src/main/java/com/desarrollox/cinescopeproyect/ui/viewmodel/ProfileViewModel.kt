package com.desarrollox.cinescopeproyect.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.desarrollox.cinescopeproyect.data.local.entity.UserEntity
import com.desarrollox.cinescopeproyect.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class ProfileUiState(
    val isLoading: Boolean = true,
    val user: UserEntity? = null,
    val viewsCount: Int = 0,
    val favoritesCount: Int = 0,
    val error: String? = null,
    val logoutSuccess: Boolean = false
)

class ProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
    }

    private fun loadProfile() {
        viewModelScope.launch {
            combine(
                repository.getCurrentUserFlow(),
                repository.getFavoriteCount(),
                repository.getWatchedCount()
            ) { user, favoritesCount, viewsCount ->
                ProfileUiState(
                    isLoading = false,
                    user = user,
                    favoritesCount = favoritesCount,
                    viewsCount = viewsCount
                )
            }.collect { state ->
                _uiState.value = state
            }
        }
    }

    fun logout() {
        repository.logout()
        _uiState.value = _uiState.value.copy(logoutSuccess = true)
    }

    fun resetLogoutSuccess() {
        _uiState.value = _uiState.value.copy(logoutSuccess = false)
    }
}
