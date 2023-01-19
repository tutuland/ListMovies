package com.tutuland.listmovies.favorite.data

import com.tutuland.listmovies.favorite.domain.FavoriteRepository

class FavoriteRepositoryImpl : FavoriteRepository {
    override suspend fun getFavoriteFor(id: Long): Boolean {
        return false
    }

    override suspend fun toggleFavoriteFor(id: Long) {

    }
}