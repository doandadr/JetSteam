package com.doanda.jetsteam.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.doanda.jetsteam.ui.theme.JetSteamTheme
import com.doanda.jetsteam.ui.theme.Shapes
import java.text.SimpleDateFormat
import java.util.*

fun formatMonthYear(timeStamp: Long): String {
    val date = Date(timeStamp * 1000)
    val sdf = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
    return sdf.format(date)
}

@Composable
fun GameItem(
    name: String,
    firstReleaseDate: Long,
    rating: Double,
    cover: String,
    modifier : Modifier = Modifier,
) {
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = MaterialTheme.shapes.medium,
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(4.dp),
        ) {
            AsyncImage(
                model = cover,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(8.dp)
                    .size(width = 150.dp, height = 200.dp)
                    .clip(Shapes.medium)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = modifier
                    .fillMaxSize()
            ) {
                Text(
                    text = name,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.subtitle1.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
                Text(
                    text = formatMonthYear(firstReleaseDate),
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Normal
                    )
                )
                Text(
                    text = "Score : ${rating.toInt()}",
                    style = MaterialTheme.typography.subtitle2.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameItemPreview() {
    JetSteamTheme() {
        GameItem(
            name = "The Legend of Zelda: Tears of the Kingdom",
            rating = 95.128467,
            cover = "https://images.igdb.com/igdb/image/upload/t_cover_big/co5vmg.jpg",
            firstReleaseDate = 1683849600,
        )
    }
}