package com.tutuland.listmovies.movie.di

import com.tutuland.listmovies.movie.data.MovieRepositoryImpl
import com.tutuland.listmovies.movie.domain.MovieRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val movieModule = module {
    singleOf(::MovieRepositoryImpl) bind MovieRepository::class
}