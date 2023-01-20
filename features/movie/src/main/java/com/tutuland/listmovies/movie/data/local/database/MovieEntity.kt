package com.tutuland.listmovies.movie.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val imageUrl: String,
    val releasedIn: String,
    val duration: String,
    val imdbLink: String,
    val resolution: String,
    val ageRestriction: String,
)