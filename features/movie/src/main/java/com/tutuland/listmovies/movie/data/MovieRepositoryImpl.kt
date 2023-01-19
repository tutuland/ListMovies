package com.tutuland.listmovies.movie.data

import com.tutuland.listmovies.movie.domain.MovieRepository
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        return emptyList()
    }
}