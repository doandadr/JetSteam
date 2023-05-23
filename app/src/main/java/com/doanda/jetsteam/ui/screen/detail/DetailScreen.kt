package com.doanda.jetsteam.ui.screen.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.doanda.jetsteam.R
import com.doanda.jetsteam.ui.common.UiState
import com.doanda.jetsteam.ui.screen.utils.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.*

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
    Box(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        Column(
            modifier = modifier.verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),

        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = cover,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(width = 200.dp, height = 250.dp)
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = rating.toInt().toString(),
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .drawBehind {
                            drawCircle(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        Color.Green,
                                        Color.Yellow
                                    )
                                ),
                                radius = 24.dp.toPx(),
                                style = Stroke(width = 4.dp.toPx())
                            )
                        }
                )
            }
            Text(
                text = name,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
            )
            Column(modifier = modifier) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Released ", color = MaterialTheme.colors.secondaryVariant)
                    Text(text = formatDayMonthYear(firstReleaseDate), textAlign = TextAlign.End)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Genres ", color = MaterialTheme.colors.secondaryVariant)
                    Text(text = genres, textAlign = TextAlign.End)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                ) {
                    Text(text = "Platforms ", color = MaterialTheme.colors.secondaryVariant)
                    Text(text = platforms, textAlign = TextAlign.End)
                }
            }
            Text(
                text = summary,
                style = MaterialTheme.typography.subtitle1
            )
        }
        FloatingActionButton(
            modifier = modifier
                .align(Alignment.BottomEnd)
                .padding(12.dp),
            onClick = updateFavoriteStatus
        ) {
            Icon(
                imageVector = if (favoriteStatus) {
                        Icons.Default.Favorite
                    } else {
                        Icons.Default.FavoriteBorder
                    },
                contentDescription = name
            )

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(12.dp),
            onClick = onBackClick
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back_to_home)
            )
        }
    }
}

fun formatDayMonthYear(timeStamp: Long): String {
    val date = Date(timeStamp * 1000)
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    return sdf.format(date)
}

