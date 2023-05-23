package com.doanda.jetsteam.di

import android.content.Context
import com.doanda.jetsteam.data.local.FavoriteDatabase
import com.doanda.jetsteam.data.repository.GameRepository

object Injection {
    fun provideRepository(context: Context): GameRepository {
        val database = FavoriteDatabase.getDatabase(context)
        return GameRepository(
            database = database,
        )
    }
}