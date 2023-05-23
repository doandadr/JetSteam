package com.doanda.jetsteam.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let {
        uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getGames()
            }
            is UiState.Success -> {
                HomeContent(
                    gameList = uiState.data,
                    modifier = modifier,
                    navigateToDetail = navigateToDetail,
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun HomeContent(
    gameList: List<Game>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
) {

}