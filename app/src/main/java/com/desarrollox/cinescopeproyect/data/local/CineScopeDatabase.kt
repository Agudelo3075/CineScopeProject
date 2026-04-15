package com.desarrollox.cinescopeproyect.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.desarrollox.cinescopeproyect.data.local.dao.*
import com.desarrollox.cinescopeproyect.data.local.entity.*

@Database(
    entities = [
        MovieEntity::class,
        UserEntity::class,
        FavoriteEntity::class,
        WatchHistoryEntity::class,
        MyListEntity::class,
        RatingEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class CineScopeDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun userDao(): UserDao
    abstract fun favoriteDao(): FavoriteDao
    abstract fun watchHistoryDao(): WatchHistoryDao
    abstract fun myListDao(): MyListDao
    abstract fun ratingDao(): RatingDao
}
