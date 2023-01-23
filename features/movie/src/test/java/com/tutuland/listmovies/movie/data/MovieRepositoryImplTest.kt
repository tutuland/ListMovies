package com.tutuland.listmovies.movie.data

import com.tutuland.listmovies.movie.data.local.MovieLocalSource
import com.tutuland.listmovies.movie.data.remote.MovieRemoteSource
import com.tutuland.listmovies.movie.fixListOfMovies
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class MovieRepositoryImplTest {
    @MockK
    lateinit var localSource: MovieLocalSource

    @MockK
    lateinit var remoteSource: MovieRemoteSource
    private lateinit var repository: MovieRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = MovieRepositoryImpl(localSource, remoteSource)
    }

    @Test
    fun `when getMovies called and localSource throws, fail`() = runTest {
        coEvery { localSource.getMovies() } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { repository.getMovies() }

        coVerify { localSource.getMovies() }
        confirmVerified(localSource, remoteSource)
    }

    @Test
    fun `when getMovies called and localSource returns results, emit them`() = runTest {
        coEvery { localSource.getMovies() } returns fixListOfMovies

        val result = repository.getMovies()

        assertEquals(fixListOfMovies, result)
        coVerify { localSource.getMovies() }
        confirmVerified(localSource, remoteSource)
    }

    @Test
    fun `when getMovies called and localSource returns no results, request from remoteSource`() =
        runTest {
            coEvery { localSource.getMovies() } returns emptyList()
            coEvery { remoteSource.getMovies() } returns fixListOfMovies

            repository.getMovies()

            coVerifyOrder {
                localSource.getMovies()
                remoteSource.getMovies()
                localSource.saveMovies(fixListOfMovies)
                localSource.getMovies()
            }
            confirmVerified(localSource, remoteSource)
        }
}