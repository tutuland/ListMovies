package com.tutuland.listmovies.list

import com.tutuland.listmovies.list.domain.model.ListItem
import com.tutuland.listmovies.movie.domain.model.Movie

const val fixId = 315162L
const val fixTitle = "Puss in Boots: The Last Wish"
const val fixImageUrl= "https://image.tmdb.org/t/p/w300/kuf6dutpsT0vSVehic3EZIqkOBt.jpg"
const val fixReleasedIn = "(2022)"
const val fixDuration = "1h 42m"
const val fixImdbLink= "https://www.imdb.com/title/tt3915174/?ref_=fn_al_tt_1"
const val fixResolution = "4k"
const val fixAgeRestriction = "L"


val fixMovie = Movie(
    id = fixId,
    title = fixTitle,
    imageUrl = fixImageUrl,
    releasedIn = fixReleasedIn,
    duration = fixDuration,
    imdbLink = fixImdbLink,
    resolution = fixResolution,
    ageRestriction = fixAgeRestriction,
)

val fixListOfMovies = listOf(fixMovie)

val fixItem = ListItem(
    movie = fixMovie,
    isFavorite = true,
)

val fixListOfItems = listOf(fixItem)