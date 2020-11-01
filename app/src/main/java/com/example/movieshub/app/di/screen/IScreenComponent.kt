package com.example.movieshub.app.di.screen

import com.example.movieshub.app.di.scope.PerScreen
import com.example.movieshub.ui.details.MovieDetailActivity
import com.example.movieshub.ui.movie_list.MoviesListActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface IScreenComponent {
    fun inject(moviesListActivity: MoviesListActivity)
    fun inject(movieDetailActivity: MovieDetailActivity)
}