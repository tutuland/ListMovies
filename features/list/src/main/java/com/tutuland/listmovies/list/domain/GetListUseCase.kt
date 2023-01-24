package com.tutuland.listmovies.list.domain

import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import com.tutuland.listmovies.list.domain.model.ListItem
import com.tutuland.listmovies.movie.domain.MovieRepository
import com.tutuland.listmovies.movie.domain.model.Movie

internal class GetListUseCase(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(): List<ListItem> =
        movieRepository.getMovies()
            .map { it.toListItem() }

    private suspend fun Movie.toListItem() = ListItem(
        movie = this,
        isFavorite = favoriteRepository.getFavoriteFor(id)
    )
}
