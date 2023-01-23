package com.tutuland.listmovies.list.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tutuland.listmovies.movie.data.local.database.MovieDao
import com.tutuland.listmovies.movie.data.local.database.MovieRoomDatabase
import io.mockk.mockk
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.koinApplication
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.check.checkModules

@RunWith(AndroidJUnit4::class)
class CheckModulesTest : KoinTest {

    // Mocking dependencies usually created by the framework
    private val mockModule = module {
        factory { mockk<MovieDao>() }
        factory { mockk<MovieRoomDatabase>() }
        factory { mockk<DataStore<Preferences>>() }
    }

    @Test
    fun verifyKoinApp() {
        koinApplication {
            modules(listModule, mockModule)
            checkModules()
        }
    }
}