package com.doanda.jetsteam.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.jetsteam.data.repository.GameRepository
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(private val repo: GameRepository)
    : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<Game>>> = MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<Game>>>
        get() = _uiState

    fun getGames() {
        viewModelScope.launch {
            repo.getGames()
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { gameList ->
                    _uiState.value = UiState.Success(gameList)
                }
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val query: StateFlow<String> get() = _searchQuery

    fun search(newSearchQuery: String) {
        _searchQuery.value = newSearchQuery
        viewModelScope.launch {
            repo.searchGames(_searchQuery.value)
                .catch {
                    _uiState.value = UiState.Error(it.message.toString())
                }
                .collect { gameList ->
                    _uiState.value = UiState.Success(gameList)
                }
        }
    }
}