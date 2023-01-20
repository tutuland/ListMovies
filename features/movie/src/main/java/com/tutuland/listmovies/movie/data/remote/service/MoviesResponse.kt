package com.tutuland.listmovies.movie.data.remote.service

import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @SerializedName("results") val results: List<MoviePayload>? = null
)
