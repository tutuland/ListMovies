package com.tutuland.listmovies.movie.data

import com.tutuland.listmovies.movie.domain.MovieRepository
import com.tutuland.listmovies.movie.domain.model.Movie

class MovieRepositoryImpl : MovieRepository {
    override suspend fun getMovies(): List<Movie> {
        flip = !flip
        if (flip) throw Exception()
        return listOf(fakeMoview1, fakeMoview2, fakeMoview3)
    }
}

var flip = true
val fakeMoview1 = Movie(
    id = 1,
    title = "Puss in Boots: The Last Wish",
    imageUrl = "https://image.tmdb.org/t/p/w300/kuf6dutpsT0vSVehic3EZIqkOBt.jpg",
    releasedIn = "2022",
    duration = "1h 42m",
    imdbLink = "https://www.imdb.com/title/tt3915174/?ref_=fn_al_tt_1",
    resolution = "4k",
    ageRestriction = "L",
)

val fakeMoview2 = fakeMoview1.copy(
    id = 2,
    title = "Avatão",
    imageUrl = "https://image.tmdb.org/t/p/w300/t6HIqrRAclMCA60NsSmeqe9RmNV.jpg"
)
val fakeMoview3 =
    fakeMoview1.copy(id = 3, title = "Oi", releasedIn = "2005", ageRestriction = "18+")