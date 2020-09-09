package com.example.movieshub.app.di.screen

import com.example.movieshub.app.di.scope.PerScreen
import com.example.movieshub.app.presentation.details.MovieDetailActivity
import com.example.movieshub.app.presentation.main.MoviesListActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface IScreenComponent {
    fun inject(moviesListActivity: MoviesListActivity)
    fun inject(movieDetailActivity: MovieDetailActivity)
}