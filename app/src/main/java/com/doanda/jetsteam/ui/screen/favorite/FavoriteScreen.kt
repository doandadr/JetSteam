package com.doanda.jetsteam.ui.screen.favorite

import ScrollToTopButton
import androidx.compose.animation.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.doanda.jetsteam.R
import com.doanda.jetsteam.model.Game
import com.doanda.jetsteam.ui.common.UiState
import com.doanda.jetsteam.ui.components.GameItem
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory
import kotlinx.coroutines.launch

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
                    gameList = uiState.data,
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
    gameList: List<Game>,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()
        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }

        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = modifier
        ) {
            if (gameList.isNotEmpty()) {
                items(gameList, key = { it.id }) { item ->
                    GameItem(
                        name = item.name,
                        firstReleaseDate = item.firstReleaseDate,
                        rating = item.rating,
                        cover = item.cover,
                        modifier = Modifier
                            .clickable {
                                navigateToDetail(item.id)
                            }
                    )
                }
            } else {
                item {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        Image(
                            painter = painterResource(R.drawable.steam_logo_transparent),
                            contentDescription = null,
                            modifier = Modifier
                                .padding(vertical = 40.dp)
                                .size(200.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.list_empty),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.subtitle1.copy(
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically(),
            modifier = Modifier
                .padding(bottom = 25.dp)
                .align(Alignment.BottomCenter)
        ) {
            ScrollToTopButton(
                onClick = {
                    scope.launch {
                        listState.animateScrollToItem(0)
                    }
                }
            )
        }
    }



}