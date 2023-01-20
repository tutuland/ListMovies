package com.tutuland.listmovies.movie.data

import com.tutuland.listmovies.movie.data.local.MovieLocalSource
import com.tutuland.listmovies.movie.data.remote.MovieRemoteSource
import com.tutuland.listmovies.movie.domain.MovieRepository
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieRepositoryImpl(
    private val localSource: MovieLocalSource,
    private val remoteSource: MovieRemoteSource,
) : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        var movies = localSource.getMovies()
        if (movies.isEmpty()) {
            localSource.saveMovies(remoteSource.getMovies())
            movies = localSource.getMovies()
        }
        return movies
    }
}
