package com.example.movieshub.app.presentation.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.example.movieshub.app.presentation.details.MovieDetailActivity
import java.lang.ref.WeakReference
import javax.inject.Inject

class MovieListRouter @Inject constructor(
    private val activityRef: WeakReference<Activity>
) {
    enum class Route{
        MOVIE_DETAIL
    }

    fun navigate(route: Route, bundle: Bundle = Bundle()) {
        when(route) {
            Route.MOVIE_DETAIL -> {
                showNextScreen(MovieDetailActivity::class.java, bundle)
            }
        }
    }

    private fun showNextScreen(clazz: Class<*>, bundle: Bundle) {
        activityRef.get()?.startActivity(Intent(activityRef.get(), clazz).putExtras(bundle))
    }
}