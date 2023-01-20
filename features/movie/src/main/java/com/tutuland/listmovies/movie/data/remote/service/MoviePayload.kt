package com.tutuland.listmovies.movie.data.remote.service

import com.google.gson.annotations.SerializedName

data class MoviePayload(
    @SerializedName("id") val id: Long? = 0,
    @SerializedName("title") val title: String? = null,
    @SerializedName("poster_path") val posterPath: String? = null,
    @SerializedName("release_date") val releaseDate: String? = null,
    @SerializedName("duration") val duration: String? = null,
    @SerializedName("imdb_link") val imdbLink: String? = null,
    @SerializedName("resolution") val resolution: String? = null,
    @SerializedName("age_restriction") val ageRestriction: String? = null,
)
