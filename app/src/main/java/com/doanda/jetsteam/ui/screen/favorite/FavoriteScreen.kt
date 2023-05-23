package com.doanda.jetsteam.ui.screen.favorite

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory

@Composable
fun FavoriteScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory.getInstance(LocalContext.current)
    ),
    navigateToDetail: (Long) -> Unit,
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getAllFavoriteCountries()
            }
            is UiState.Success -> {
                FavoriteContent(
                    countryList = uiState.data,
                    navigateToDetail = navigateToDetail,
                    modifier = modifier
                )
            }
            is UiState.Error -> {}
        }
    }
}

//@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteContent(
    countryList: List<Game>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {}