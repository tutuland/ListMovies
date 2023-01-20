package com.tutuland.listmovies.movie.di

import com.tutuland.listmovies.movie.data.MovieRepositoryImpl
import com.tutuland.listmovies.movie.data.remote.MovieRemoteSource
import com.tutuland.listmovies.movie.data.remote.MovieRemoteSourceImpl
import com.tutuland.listmovies.movie.data.remote.service.createMoviesService
import com.tutuland.listmovies.movie.domain.MovieRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val movieModule = module {
    factory { createMoviesService() }
    factoryOf(::MovieRemoteSourceImpl) bind MovieRemoteSource::class
    factoryOf(::MovieRepositoryImpl) bind MovieRepository::class
}