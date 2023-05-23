package com.doanda.jetsteam.ui.screen.home

import ScrollToTopButton
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.doanda.jetsteam.ui.components.SearchBar
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory
import kotlinx.coroutines.launch

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
                    viewModel = viewModel
                )
            }
            is UiState.Error -> {}
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeContent(
    gameList: List<Game>,
    modifier: Modifier = Modifier,
    navigateToDetail: (Long) -> Unit,
    viewModel: HomeViewModel,
) {
    Box(modifier = modifier) {
        val query by viewModel.query.collectAsState()
        val scope = rememberCoroutineScope()
        val listState = rememberLazyListState()

        val showButton: Boolean by remember {
            derivedStateOf { listState.firstVisibleItemIndex > 0 }
        }
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                SearchBar(
                    query = query,
                    onQueryChange = viewModel::search,
                    modifier = Modifier.background(MaterialTheme.colors.primary)
                )
            }
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
                            .animateItemPlacement(tween(durationMillis = 300))
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
                .padding(bottom = 20.dp)
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