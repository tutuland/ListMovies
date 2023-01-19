package com.tutuland.listmovies.movie.data.model

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val results: List<MoviePayload>? = null
)
