package com.tutuland.listmovies.movie.data.local.database

import com.tutuland.listmovies.movie.fixListOfMovieEntities
import com.tutuland.listmovies.movie.fixListOfMovies
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

internal class MovieDatabaseSourceTest {
    @MockK
    lateinit var database: MovieDao
    lateinit var source: MovieDatabaseSource

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        source = MovieDatabaseSource(database)
    }

    @Test
    fun `when getMovies called and database throws, fail`() = runTest {
        coEvery { database.getMovies() } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { source.getMovies() }

        coVerifyOrder { database.getMovies() }
        confirmVerified(database)
    }

    @Test
    fun `when getMovies called and database returns entities, process them and emit`() = runTest {
        coEvery { database.getMovies() } returns fixListOfMovieEntities

        val result = source.getMovies()

        assertEquals(fixListOfMovies, result)
        coVerifyOrder { database.getMovies() }
        confirmVerified(database)
    }

    @Test
    fun `when getMovies called and database returns an empty list, emit an empty list`() = runTest {
        coEvery { database.getMovies() } returns emptyList()

        val result = source.getMovies()

        assertEquals(emptyList(), result)
        coVerifyOrder { database.getMovies() }
        confirmVerified(database)
    }

    @Test
    fun `when getMovies called, map to entity and delegate to database`() = runTest {
        source.saveMovies(fixListOfMovies)

        coVerifyOrder {
            database.deleteMovies()
            database.saveMovies(fixListOfMovieEntities)
        }
        confirmVerified(database)
    }
}