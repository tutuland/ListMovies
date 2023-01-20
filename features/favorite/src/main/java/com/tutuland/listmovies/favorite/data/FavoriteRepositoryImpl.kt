package com.tutuland.listmovies.favorite.data

import com.tutuland.listmovies.favorite.domain.FavoriteRepository

class FavoriteRepositoryImpl : FavoriteRepository {
    override suspend fun getFavoriteFor(id: Long): Boolean {
        return mapa[id] ?: false
    }

    override suspend fun toggleFavoriteFor(id: Long) {
        mapa[id] = getFavoriteFor(id).not()
    }
}

val mapa = mutableMapOf<Long, Boolean>()