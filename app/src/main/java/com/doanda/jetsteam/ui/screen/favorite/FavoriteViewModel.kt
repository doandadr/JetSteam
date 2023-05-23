package com.doanda.jetsteam.ui.screen.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.jetsteam.data.repository.GameRepository
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repo: GameRepository)
    : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Game>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Game>>>
        get() = _uiState

    fun getAllFavoriteCountries() {
        viewModelScope.launch {
            repo.getAllFavorites()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { favoriteGames ->
                    _uiState.value = UiState.Success(favoriteGames)
                }
        }
    }
}