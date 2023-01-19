package com.tutuland.listmovies.list.di

import com.tutuland.listmovies.favorite.di.favoriteModule
import com.tutuland.listmovies.list.domain.GetListUseCase
import com.tutuland.listmovies.list.domain.ToggleFavoriteUseCase
import com.tutuland.listmovies.list.presentation.ListViewModel
import com.tutuland.listmovies.movie.di.movieModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val listModule = module {
    includes(movieModule, favoriteModule)
    factoryOf(::GetListUseCase)
    factoryOf(::ToggleFavoriteUseCase)
    viewModelOf(::ListViewModel)
}
