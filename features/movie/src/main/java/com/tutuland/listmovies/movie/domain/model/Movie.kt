package com.tutuland.listmovies.movie.domain.model

data class Movie(
    val id: Long,
    val title: String,
    val imageUrl: String,
    val releasedIn: String,
    val duration: String,
    val imdbLink: String,
    val resolution: String,
    val ageRestriction: String,
)
