package com.doanda.jetsteam.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.jetsteam.data.repository.GameRepository
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DetailViewModel(private val repo: GameRepository)
    : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<Game>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<Game>>
        get() = _uiState

    private val _favoriteStatus = MutableStateFlow(false)
    val favoriteStatus: StateFlow<Boolean>
        get() = _favoriteStatus
    fun getGameDetail(gameId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repo.getGameDetail(gameId))
        }
    }

    fun getFavoriteStatus(gameId: Long) {
        viewModelScope.launch {
            _favoriteStatus.value = repo.isGameFavorited(gameId).first()
        }
    }

    fun updateFavoriteStatus(game: Game) {
        viewModelScope.launch {
            if (_favoriteStatus.value) {
                repo.deleteFavorite(game)
            } else {
                repo.insertFavorite(game)
            }
        }
    }
}