package com.tutuland.listmovies.list.domain

import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import com.tutuland.listmovies.list.fixId
import com.tutuland.listmovies.list.fixListOfItems
import com.tutuland.listmovies.list.fixListOfMovies
import com.tutuland.listmovies.movie.domain.MovieRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class GetListUseCaseTest {
    @MockK
    lateinit var movieRepository: MovieRepository

    @MockK
    lateinit var favoriteRepository: FavoriteRepository
    private lateinit var getList: GetListUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        getList = GetListUseCase(movieRepository, favoriteRepository)
    }

    @Test
    fun `when getList is called and movieRepository throws, fail`() = runTest {
        coEvery { movieRepository.getMovies() } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { getList() }

        coVerify {
            movieRepository.getMovies()
        }
        confirmVerified(movieRepository, favoriteRepository)
    }

    @Test
    fun `when getList is called and favoriteRepository throws, fail`() = runTest {
        coEvery { movieRepository.getMovies() } returns fixListOfMovies
        coEvery { favoriteRepository.getFavoriteFor(fixId) } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { getList() }

        coVerify {
            movieRepository.getMovies()
            favoriteRepository.getFavoriteFor(fixId)
        }
        confirmVerified(movieRepository, favoriteRepository)
    }


    @Test
    fun `when getList is called and both repositories return, map list of listItems`() = runTest {
        coEvery { movieRepository.getMovies() } returns fixListOfMovies
        coEvery { favoriteRepository.getFavoriteFor(fixId) } returns true

        val response = getList()

        assertEquals(fixListOfItems, response)
        coVerify {
            movieRepository.getMovies()
            favoriteRepository.getFavoriteFor(fixId)
        }
        confirmVerified(movieRepository, favoriteRepository)
    }
}