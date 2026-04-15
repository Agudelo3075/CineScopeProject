package com.desarrollox.cinescopeproyect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ratings")
data class RatingEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val movieId: Long,
    val movieTitle: String,
    val stars: Int,
    val comment: String = "",
    val ratedAt: Long = System.currentTimeMillis()
)
