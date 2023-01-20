package com.tutuland.listmovies.movie.data.remote

import com.tutuland.listmovies.movie.domain.model.Movie

interface MovieRemoteSource {
    suspend fun getMovies(): List<Movie>
}