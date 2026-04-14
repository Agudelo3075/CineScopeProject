package com.desarrollox.cinescopeproyect.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watch_history")
data class WatchHistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val movieId: Long,
    val title: String,
    val year: Int,
    val genre: String,
    val type: String,
    val progress: Float = 0f,
    val isCompleted: Boolean = false,
    val watchedAt: Long = System.currentTimeMillis(),
    val color1: Long = 0xFFE53935,
    val color2: Long = 0xFF120A0A
)
