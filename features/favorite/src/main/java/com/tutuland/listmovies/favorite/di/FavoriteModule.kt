package com.tutuland.listmovies.favorite.di

import android.content.Context
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.preferencesDataStoreFile
import com.tutuland.listmovies.favorite.data.FavoriteRepositoryImpl
import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favoriteModule = module {
    single { PreferenceDataStoreFactory.create { get<Context>().preferencesDataStoreFile("ListMovies") } }
    singleOf(::FavoriteRepositoryImpl) bind FavoriteRepository::class
}