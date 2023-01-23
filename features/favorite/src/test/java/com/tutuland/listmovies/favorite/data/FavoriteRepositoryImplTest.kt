package com.tutuland.listmovies.favorite.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.coVerifyOrder
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class FavoriteRepositoryImplTest {
    @MockK
    lateinit var dataStore: DataStore<Preferences>

    @MockK
    lateinit var prefs: MutablePreferences
    private lateinit var repository: FavoriteRepositoryImpl
    private val fixId = 12345L
    private val fixPrefsKey = booleanPreferencesKey(fixId.toString())

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        repository = FavoriteRepositoryImpl(dataStore)
    }

    @Test
    fun `when dataStore data throws, fail`() = runTest {
        coEvery { dataStore.data } throws IllegalStateException()

        assertFailsWith<IllegalStateException> { repository.getFavoriteFor(fixId) }

        coVerify { dataStore.data }
        confirmVerified(dataStore, prefs)
    }

    @Test
    fun `when dataStore data returns nothing, fail`() = runTest {
        coEvery { dataStore.data } returns flow { }

        assertFailsWith<NoSuchElementException> { repository.getFavoriteFor(fixId) }

        coVerify { dataStore.data }
        confirmVerified(dataStore, prefs)
    }

    @Test
    fun `when dataStore has no value for id, return false`() = runTest {
        coEvery { dataStore.data } returns flowOf(prefs)
        coEvery { prefs[fixPrefsKey] } returns null

        val result = repository.getFavoriteFor(fixId)

        assertFalse(result)
        coVerifyOrder {
            dataStore.data
            prefs[fixPrefsKey]
        }
        confirmVerified(dataStore, prefs)
    }

    @Test
    fun `when dataStore has value for id, return it`() = runTest {
        coEvery { dataStore.data } returns flowOf(prefs)
        coEvery { prefs[fixPrefsKey] } returns true

        val result = repository.getFavoriteFor(fixId)

        assertTrue(result)
        coVerifyOrder {
            dataStore.data
            prefs[fixPrefsKey]
        }
        confirmVerified(dataStore, prefs)
    }

    @Test
    fun `when toggleFavoriteFor it, store it on dataStore`() = runTest {
        coEvery { dataStore.updateData(any()) } returns prefs

        repository.toggleFavoriteFor(fixId)

        coVerifyOrder {
            dataStore.updateData(any())
        }
        confirmVerified(dataStore, prefs)
    }
}