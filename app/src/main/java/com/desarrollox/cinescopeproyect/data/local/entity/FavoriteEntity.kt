package com.desarrollox.cinescopeproyect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: Long,
    val movieId: Long,
    val title: String,
    val year: Int,
    val genre: String,
    val rating: Float,
    val type: String,
    val color1: Long = 0xFFE53935,
    val color2: Long = 0xFF120A0A,
    val addedAt: Long = System.currentTimeMillis()
)
