package com.tutuland.listmovies.movie.data.remote.service

import com.tutuland.listmovies.movie.fixListOfMovies
import com.tutuland.listmovies.movie.fixMovieResponse
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MovieServiceSourceTest {
    @MockK
    lateinit var service: MoviesService
    lateinit var source: MovieServiceSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        source = MovieServiceSource(service)
    }

    @Test
    fun `when getMovies called and api throws, fail`() = runTest {
        coEvery { service.getMovies() } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { source.getMovies() }

        coVerifyOrder { service.getMovies() }
        confirmVerified(service)
    }

    @Test
    fun `when getMovies called and api returns results, map them to movies`() = runTest {
        coEvery { service.getMovies() } returns fixMovieResponse

        val response = source.getMovies()

        assertEquals(fixListOfMovies, response)
        coVerifyOrder { service.getMovies() }
        confirmVerified(service)
    }
}