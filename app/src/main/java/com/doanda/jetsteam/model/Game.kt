package com.doanda.jetsteam.model

import androidx.room.Entity

@Entity(tableName = "game")
data class Game(
    val id: Int,
    val name: String,
    val summary: String,
    val firstReleaseDate: Int,
    val rating: Any,
    val cover: String,
    val genres: List<String>,
    val platforms: List<String>,
)
