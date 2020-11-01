package com.example.movieshub.app.di.screen

import android.app.Activity
import com.example.movieshub.ui.base.BaseActivity
import com.example.movieshub.app.di.scope.PerScreen
import com.example.movieshub.ui.movie_list.MovieListRouter
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

@Module
class ScreenModule(private val baseActivity: BaseActivity) {

    @PerScreen
    @Provides
    fun providesActivity(): BaseActivity {
        return baseActivity
    }

    @PerScreen
    @Provides
    fun providesActivityRef(): WeakReference<Activity> {
        return WeakReference(baseActivity)
    }

    @PerScreen
    @Provides
    fun providesMovieListRouter(): MovieListRouter {
        return MovieListRouter(WeakReference(baseActivity))
    }
}