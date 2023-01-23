package com.tutuland.listmovies.list.domain

import com.tutuland.listmovies.favorite.domain.FavoriteRepository
import com.tutuland.listmovies.list.fixId
import com.tutuland.listmovies.list.fixItem
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith

class ToggleFavoriteUseCaseTest {
    @MockK lateinit var favoriteRepository: FavoriteRepository
    private lateinit var toggleFavorite: ToggleFavoriteUseCase

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        toggleFavorite = ToggleFavoriteUseCase(favoriteRepository)
    }

    @Test
    fun `when toggleFavorite is called delegate to favoriteRepository`() = runTest {
        coEvery { favoriteRepository.toggleFavoriteFor(fixId) } returns Unit

        toggleFavorite(fixItem)

        coVerify { favoriteRepository.toggleFavoriteFor(fixId) }
        confirmVerified(favoriteRepository)
    }

    @Test
    fun `when toggleFavorite is called and favoriteRepository throws, fail`() = runTest {
        coEvery { favoriteRepository.toggleFavoriteFor(fixId) } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { toggleFavorite(fixItem) }

        coVerify { favoriteRepository.toggleFavoriteFor(fixId) }
        confirmVerified(favoriteRepository)
    }
}