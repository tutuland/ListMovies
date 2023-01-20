package com.tutuland.listmovies.movie.data.local

import com.tutuland.listmovies.movie.domain.model.Movie

interface MovieLocalSource {
    suspend fun getMovies(): List<Movie>
    suspend fun saveMovies(list: List<Movie>)
}