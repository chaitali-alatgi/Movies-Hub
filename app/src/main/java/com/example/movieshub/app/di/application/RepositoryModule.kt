package com.example.movieshub.app.di.application

import com.example.movieshub.data.database.AppDatabase
import com.example.movieshub.data.network.NetworkApiRequest
import com.example.movieshub.data.mapper.MovieMapper
import com.example.movieshub.data.repository.MovieRepositoryImpl
import com.example.movieshub.domain.repository.MovieRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesMovieRepository(
        movieMapper: MovieMapper,
        networkApiRequest: NetworkApiRequest,
        appDatabase: AppDatabase
    ): MovieRepository {
        return MovieRepositoryImpl(movieMapper, networkApiRequest, appDatabase)
    }
}