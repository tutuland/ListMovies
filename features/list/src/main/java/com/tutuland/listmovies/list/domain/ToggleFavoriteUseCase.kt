package com.tutuland.listmovies.list.domain

import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import com.tutuland.listmovies.list.domain.model.ListItem

class ToggleFavoriteUseCase(
    private val favoriteRepository: FavoriteRepository,
) {
    suspend operator fun invoke(item: ListItem) =
        favoriteRepository.toggleFavoriteFor(item.movie.id)
}