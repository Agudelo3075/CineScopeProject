package com.desarrollox.cinescopeproyect.data.remote.model

import com.google.gson.annotations.SerializedName

data class TmdMovieResponse(
    val results: List<TmdMovieResult>
)

data class TmdMovieResult(
    val id: Long,
    val title: String,
    @SerializedName("overview") val synopsis: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val rating: Float,
    @SerializedName("genre_ids") val genreIds: List<Int>
)
