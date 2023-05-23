package com.doanda.jetsteam.ui.screen.detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doanda.jetsteam.ui.common.UiState
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory

@Composable
fun DetailScreen(
    gameId: Long,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToBack: () -> Unit,
) {
    viewModel.getFavoriteStatus(gameId)
    val favoriteStatus by viewModel.favoriteStatus.collectAsState(initial = false)

    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getGameDetail(gameId)
            }
            is UiState.Success -> {
                val game = uiState.data
                DetailContent(
                    name = game.name,
                    summary = game.summary,
                    firstReleaseDate = game.firstReleaseDate,
                    rating = game.rating,
                    cover = game.cover,
                    genres = game.genres,
                    platforms  = game.platforms,
                    favoriteStatus  = favoriteStatus,
                    updateFavoriteStatus = { viewModel.updateFavoriteStatus(game) },
                    onBackClick = navigateToBack,
                )
            }
            is UiState.Error -> {

            }
        }
    }
}

@Composable
fun DetailContent(
    name: String,
    summary: String,
    firstReleaseDate: Long,
    rating: Double,
    cover: String,
    genres: String,
    platforms: String,
    favoriteStatus : Boolean,
    updateFavoriteStatus: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {

}