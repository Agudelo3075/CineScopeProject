package com.desarrollox.cinescopeproyect.data.remote

import com.desarrollox.cinescopeproyect.data.remote.model.MovieBackend
import retrofit2.Response
import retrofit2.http.*

interface BackendApiService {
    
    @GET("movies")
    suspend fun getMovies(): Response<List<MovieBackend>>

    @GET("movies/{id}")
    suspend fun getMovie(@Path("id") id: Int): Response<MovieBackend>

    @POST("movies")
    suspend fun createMovie(@Body movie: MovieBackend): Response<MovieBackend>

    @PUT("movies/{id}")
    suspend fun updateMovie(@Path("id") id: Int, @Body movie: MovieBackend): Response<MovieBackend>

    @DELETE("movies/{id}")
    suspend fun deleteMovie(@Path("id") id: Int): Response<Void>
}
