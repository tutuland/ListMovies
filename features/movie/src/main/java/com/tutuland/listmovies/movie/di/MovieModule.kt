package com.tutuland.listmovies.movie.di

import com.tutuland.listmovies.movie.data.MovieRepositoryImpl
import com.tutuland.listmovies.movie.data.local.MovieLocalSource
import com.tutuland.listmovies.movie.data.local.database.MovieDatabaseSource
import com.tutuland.listmovies.movie.data.local.database.MovieRoomDatabase
import com.tutuland.listmovies.movie.data.local.database.createMovieDatabase
import com.tutuland.listmovies.movie.data.remote.MovieRemoteSource
import com.tutuland.listmovies.movie.data.remote.service.MovieServiceSource
import com.tutuland.listmovies.movie.data.remote.service.createMoviesService
import com.tutuland.listmovies.movie.domain.MovieRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val movieModule = module {
    single { createMovieDatabase(get()) }
    factory { get<MovieRoomDatabase>().movieDao() }
    factory { createMoviesService() }
    factoryOf(::MovieDatabaseSource) bind MovieLocalSource::class
    factoryOf(::MovieServiceSource) bind MovieRemoteSource::class
    factoryOf(::MovieRepositoryImpl) bind MovieRepository::class
}