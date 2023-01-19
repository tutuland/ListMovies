package com.tutuland.listmovies

import android.app.Application
import com.tutuland.listmovies.list.di.listModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ListMoviesApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@ListMoviesApp)
            modules(listModule)
        }
    }
}