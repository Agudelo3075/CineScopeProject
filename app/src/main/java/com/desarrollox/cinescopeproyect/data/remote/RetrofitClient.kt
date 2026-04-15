package com.desarrollox.cinescopeproyect.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    
    // TODO: REEMPLAZA ESTA CLAVE POR TU PROPIA API KEY DE TMDB
    // Obtener en: https://www.themoviedb.org/settings/api
    const val API_KEY = "f3bde517be7065bdfdd1902c11f990b3" // Ejemplo, debe ser reemplazada por el usuario si falla

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    val movieApiService: MovieApiService = retrofit.create(MovieApiService::class.java)
    
    fun getImageUrl(posterPath: String?): String {
        return if (posterPath != null) "https://image.tmdb.org/t/p/w500$posterPath" else ""
    }
}
