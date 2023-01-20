package com.tutuland.listmovies.movie.data.remote

import com.tutuland.listmovies.movie.data.remote.service.MoviePayload
import com.tutuland.listmovies.movie.data.remote.service.MoviesService
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieRemoteSourceImpl(private val service: MoviesService) : MovieRemoteSource {
    override suspend fun getMovies(): List<Movie> {
        val response = service.getMovies()
        return response.results.orEmpty().map(::toMovie)
    }

    private fun toMovie(payload: MoviePayload) = Movie(
        id = payload.id ?: 0,
        title = payload.title.orEmpty(),
        imageUrl = payload.posterPath.orEmpty(),
        releasedIn = payload.releaseDate.orEmpty().toYear(),
        duration = payload.duration.orEmpty(),
        imdbLink = payload.imdbLink.orEmpty(),
        resolution = payload.resolution.orEmpty(),
        ageRestriction = payload.ageRestriction.orEmpty(),
    )

    private fun String.toYear() = take(4).let { "($it)" }
}