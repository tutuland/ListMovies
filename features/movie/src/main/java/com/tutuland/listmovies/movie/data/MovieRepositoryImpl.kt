package com.tutuland.listmovies.movie.data

import com.tutuland.listmovies.movie.data.remote.MovieRemoteSource
import com.tutuland.listmovies.movie.domain.MovieRepository
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieRepositoryImpl(
    private val remoteSource: MovieRemoteSource,
) : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        return remoteSource.getMovies()
    }
}
