package com.desarrollox.cinescopeproyect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val year: Int,
    val genre: String,
    val duration: String,
    val rating: Float,
    val director: String,
    val synopsis: String,
    val cast: String,
    val isPopular: Boolean = false,
    val isTopRated: Boolean = false,
    val isNewRelease: Boolean = false,
    val color1: Long = 0xFFE53935,
    val color2: Long = 0xFF120A0A,
    val imageUrl: String = ""
)
