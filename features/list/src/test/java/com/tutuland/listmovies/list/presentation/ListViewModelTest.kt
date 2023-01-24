package com.tutuland.listmovies.list.presentation

import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.ReceiveTurbine
import app.cash.turbine.test
import com.tutuland.listmovies.list.domain.GetListUseCase
import com.tutuland.listmovies.list.domain.ToggleFavoriteUseCase
import com.tutuland.listmovies.list.domain.model.ListItem
import com.tutuland.listmovies.list.fixItem
import com.tutuland.listmovies.list.fixListOfItems
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import kotlin.test.assertEquals
import kotlin.test.assertIs

@RunWith(AndroidJUnit4::class)
class ListViewModelTest {
    @MockK
    lateinit var getList: GetListUseCase

    @MockK
    lateinit var toggleFavorite: ToggleFavoriteUseCase
    private lateinit var viewModel: ListViewModel
    private lateinit var currState: ListViewState

    @Test
    fun `when viewModel is initialized, fetchContent is called`() = runTest {
        setupViewModel(initialList = fixListOfItems)

        viewModel.state.test {
            expect(showContent(fixListOfItems))
            expectNoEvents()
        }

        viewModel.action.test {
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    @Test
    fun `when fetchContent throws, showRetry`() = runTest {
        setupViewModel(fetchShouldThrow = true)

        viewModel.state.test {
            expect(showRetry())
            expectNoEvents()
        }

        viewModel.action.test {
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    @Test
    fun `when viewStarted is called, fetchContent is called again`() = runTest {
        setupViewModel(initialList = listOf(), secondList = fixListOfItems)

        viewModel.state.test {
            expect(showContent(listOf()))
            viewModel.viewStarted()
            expect(showContent(fixListOfItems))
            expectNoEvents()
        }

        viewModel.action.test {
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    @Test
    fun `when favoriteClicked is called, toggleFavorite and fetchContent again`() = runTest {
        setupViewModel(initialList = listOf(), secondList = fixListOfItems)

        viewModel.state.test {
            expect(showContent(listOf()))
            viewModel.favoriteClicked(fixItem)
            expect(showContent(fixListOfItems))
            expectNoEvents()
        }

        viewModel.action.test {
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
            toggleFavorite(fixItem)
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    @Test
    fun `when imdbClicked is called, emit OpenLink action`() = runTest {
        setupViewModel()

        viewModel.state.test {
            expect(showContent(listOf()))
            expectNoEvents()
        }

        viewModel.action.test {
            viewModel.imdbClicked(fixItem)
            assertIs<ListViewAction.OpenLink>(awaitItem())
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    @Test
    fun `when filterTyped is called, emit filtered state`() = runTest {
        setupViewModel(initialList = fixListOfItems)

        viewModel.state.test {
            expect(showContent(fixListOfItems))
            viewModel.filterTyped("filter")
            expect(showContent(fixListOfItems, filter = "filter"))
            expectNoEvents()
        }

        viewModel.action.test {
            expectNoEvents()
        }

        coVerifyOrder {
            getList()
        }
        confirmVerified(getList, toggleFavorite)
    }

    private fun setupViewModel(
        initialList: List<ListItem> = listOf(),
        secondList: List<ListItem> = listOf(),
        fetchShouldThrow: Boolean = false,
    ) {
        MockKAnnotations.init(this, relaxed = true)
        if (fetchShouldThrow) coEvery { getList() } throws Exception()
        else coEvery { getList() } returns initialList
        currState = ListViewState()
        viewModel = ListViewModel(getList, toggleFavorite)
        coEvery { getList() } returns secondList
    }

    private fun showRetry() =
        currState.copy(showLoading = false, showRetry = true, _items = listOf())

    private fun showContent(content: List<ListItem>, filter: String = "") =
        currState.copy(showLoading = false, showRetry = false, _items = content, filter = filter)

    private suspend fun ReceiveTurbine<ListViewState>.expect(expectedState: ListViewState) {
        currState = awaitItem()
        assertEquals(expectedState, currState)
    }
}