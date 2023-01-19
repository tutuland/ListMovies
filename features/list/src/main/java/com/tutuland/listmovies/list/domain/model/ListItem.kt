package com.tutuland.listmovies.list.domain.model

import com.tutuland.listmovies.movie.domain.model.Movie

data class ListItem(
    val movie: Movie,
    val isFavorite: Boolean,
)
