package com.tutuland.listmovies.movie.data.remote

import com.tutuland.listmovies.movie.domain.model.Movie

internal interface MovieRemoteSource {
    suspend fun getMovies(): List<Movie>
}