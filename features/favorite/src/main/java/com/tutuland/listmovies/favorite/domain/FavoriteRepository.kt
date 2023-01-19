package com.tutuland.listmovies.favorite.domain

interface FavoriteRepository {
    suspend fun getFavoriteFor(id: Long): Boolean
    suspend fun toggleFavoriteFor(id: Long)
}