package com.tutuland.listmovies.movie.domain

import com.tutuland.listmovies.movie.domain.model.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}
