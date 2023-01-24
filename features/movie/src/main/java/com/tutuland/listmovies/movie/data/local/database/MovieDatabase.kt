package com.tutuland.listmovies.movie.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

internal fun createMovieDatabase(context: Context): MovieRoomDatabase = Room
    .databaseBuilder(context, MovieRoomDatabase::class.java, "MovieDatabase")
    .fallbackToDestructiveMigration()
    .build()

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieRoomDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}
