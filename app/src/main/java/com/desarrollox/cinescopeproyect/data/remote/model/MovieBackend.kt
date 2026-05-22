package com.desarrollox.cinescopeproyect.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieBackend(
    @SerializedName("id") val id: Int? = null, // null when creating
    @SerializedName("title") val title: String,
    @SerializedName("director") val director: String,
    @SerializedName("year") val year: Int
)
