package com.desarrollox.cinescopeproyect.data.local

import android.content.Context
import androidx.room.Room

object DatabaseProvider {
    @Volatile
    private var INSTANCE: CineScopeDatabase? = null

    fun getDatabase(context: Context): CineScopeDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                CineScopeDatabase::class.java,
                "cinescope_database"
            )
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }

    fun getMovieDao(context: Context) = getDatabase(context).movieDao()
    fun getUserDao(context: Context) = getDatabase(context).userDao()
    fun getFavoriteDao(context: Context) = getDatabase(context).favoriteDao()
    fun getWatchHistoryDao(context: Context) = getDatabase(context).watchHistoryDao()
    fun getMyListDao(context: Context) = getDatabase(context).myListDao()
    fun getRatingDao(context: Context) = getDatabase(context).ratingDao()
}
