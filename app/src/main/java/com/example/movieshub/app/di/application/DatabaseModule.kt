package com.example.movieshub.app.di.application

import com.example.movieshub.ui.BaseApplication
import com.example.movieshub.data.source.database.AppDatabase
import com.example.movieshub.data.source.database.MovieDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule(private val baseApplication: BaseApplication) {

    @Singleton
    @Provides
    fun provideRoomDatabase(): AppDatabase {
        return AppDatabase.getInstance(baseApplication)
    }

    @Singleton
    @Provides
    fun providesMovieDao(appDatabase: AppDatabase): MovieDao {
        return appDatabase.movieDao()
    }
}