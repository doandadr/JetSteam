package com.doanda.jetsteam.data.local

import androidx.room.*
import com.doanda.jetsteam.model.Game
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavorite(game: Game)

    @Delete
    suspend fun deleteFavorite(game: Game)

    @Query("SELECT * FROM Game")
    fun getAllFavorites(): Flow<List<Game>>

    @Query("SELECT EXISTS(SELECT * FROM Game WHERE id = :id)")
    fun isGameFavorited(id: Long): Flow<Boolean>
}