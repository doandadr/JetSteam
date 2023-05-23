package com.doanda.jetsteam.data.repository

import com.doanda.jetsteam.data.local.FavoriteDatabase
import com.doanda.jetsteam.model.FakeGameData
import com.doanda.jetsteam.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext

class GameRepository(private val database: FavoriteDatabase) {

    private val gameList = mutableListOf<Game>()

    init {
        if (gameList.isEmpty()) {
            FakeGameData.dummyGames.forEach { game ->
                gameList.add(
                    Game(
                        id = game.id,
                        cover = game.cover,
                        firstReleaseDate = game.firstReleaseDate,
                        genres = game.genres,
                        name = game.name,
                        platforms = game.platforms,
                        rating = game.rating,
                        summary = game.summary,
                    )
                )
            }
        }
    }

    fun getGames(): Flow<List<Game>> {
        return flowOf(gameList)
    }

    fun getGameDetail(gameId: Long): Game {
        return gameList.first { game ->
            game.id == gameId
        }
    }

    fun searchCountries(query: String): Flow<List<Game>> {
        return flow {
            val countries = gameList.filter {
                it.name.contains(query, ignoreCase = true)
            }
            emit(countries)
        }
    }

    suspend fun insertFavorite(game: Game) {
        withContext(Dispatchers.IO) {
            database.favoriteDao().insertFavorite(game)
        }
    }

    suspend fun deleteFavorite(game: Game) {
        withContext(Dispatchers.IO) {
            database.favoriteDao().deleteFavorite(game)
        }
    }

    fun getAllFavorites(): Flow<List<Game>> {
        return database.favoriteDao().getAllFavorites()
    }

    fun isGameFavorited(gameId: Long): Flow<Boolean> {
        return database.favoriteDao().isGameFavorited(gameId)
    }

    fun searchGames(searchQuery: String): Flow<List<Game>> {
        return flow {
            val searchResults = gameList.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            }
            emit(searchResults)
        }
    }
}