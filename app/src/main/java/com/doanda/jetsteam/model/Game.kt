package com.doanda.jetsteam.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "game")
data class Game(
    @PrimaryKey
    val id: Long,
    val name: String,
    val summary: String,
    val firstReleaseDate: Long,
    val rating: Double,
    val cover: String,
    val genres: String,
    val platforms: String,
)
