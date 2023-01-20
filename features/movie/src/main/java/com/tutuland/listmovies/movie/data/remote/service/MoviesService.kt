package com.tutuland.listmovies.movie.data.remote.service

import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

const val BASE_URL = "http://demo5993557.mockable.io/"

interface MoviesService {
    @GET("movies")
    suspend fun getMovies(): MoviesResponse
}

fun createMoviesService(baseUrl: HttpUrl = BASE_URL.toHttpUrl()): MoviesService = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create())
    .build()
    .create(MoviesService::class.java)
