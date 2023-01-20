package com.tutuland.listmovies.favorite.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import kotlinx.coroutines.flow.first

class FavoriteRepositoryImpl(private val dataStore: DataStore<Preferences>) : FavoriteRepository {
    override suspend fun getFavoriteFor(id: Long): Boolean {
        val isFavoriteKey = booleanPreferencesKey(id.toString())
        return dataStore.data.first().let { prefs -> prefs[isFavoriteKey] ?: false }
    }

    override suspend fun toggleFavoriteFor(id: Long) {
        val isFavoriteKey = booleanPreferencesKey(id.toString())
        dataStore.edit { prefs ->
            val isFavorite = prefs[isFavoriteKey] ?: false
            prefs[isFavoriteKey] = !isFavorite
        }
    }
}