package com.tutuland.listmovies.movie.data.local.database

import com.tutuland.listmovies.movie.data.local.MovieLocalSource
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieDatabaseSource(private val database: MovieDao) : MovieLocalSource {
    override suspend fun getMovies(): List<Movie> {
        return database.getMovies().toDomain()
    }

    override suspend fun saveMovies(list: List<Movie>) {
        database.deleteMovies()
        database.saveMovies(list.toData())
    }

    private fun List<Movie>.toData(): List<MovieEntity> = map { movie ->
        MovieEntity(
            id = movie.id,
            title = movie.title,
            imageUrl = movie.imageUrl,
            releasedIn = movie.releasedIn,
            duration = movie.duration,
            imdbLink = movie.imdbLink,
            resolution = movie.resolution,
            ageRestriction = movie.ageRestriction,
        )
    }

    private fun List<MovieEntity>.toDomain(): List<Movie> = map { movie ->
        Movie(
            id = movie.id,
            title = movie.title,
            imageUrl = movie.imageUrl,
            releasedIn = movie.releasedIn,
            duration = movie.duration,
            imdbLink = movie.imdbLink,
            resolution = movie.resolution,
            ageRestriction = movie.ageRestriction,
        )
    }
}