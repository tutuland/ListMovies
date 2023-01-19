package com.tutuland.listmovies.favorite.di

import com.tutuland.listmovies.favorite.data.FavoriteRepositoryImpl
import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val favoriteModule = module {
    singleOf(::FavoriteRepositoryImpl) bind FavoriteRepository::class
}