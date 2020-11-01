package com.example.movieshub.app.di.application

import com.example.movieshub.data.source.database.AppDatabase
import com.example.movieshub.data.source.network.NetworkDataSource
import com.example.movieshub.data.mapper.MovieMapper
import com.example.movieshub.data.repository.MovieRepositoryImpl
import com.example.movieshub.data.source.database.DiskDataSource
import com.example.movieshub.domain.repository.MovieRepository
import com.example.movieshub.domain.usecase.movie_list.GetMovieListUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesMovieRepository(
        movieMapper: MovieMapper,
        networkDataSource: NetworkDataSource,
        diskDataSource: DiskDataSource
    ): MovieRepository {
        return MovieRepositoryImpl(movieMapper, networkDataSource, diskDataSource)
    }
}