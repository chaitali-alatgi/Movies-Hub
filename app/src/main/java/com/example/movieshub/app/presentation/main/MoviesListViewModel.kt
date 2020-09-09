package com.example.movieshub.app.presentation.main

import android.app.Activity
import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import com.example.movieshub.app.presentation.details.MovieDetailActivity.Companion.EXTRA_MOVIE_DETAILS
import com.example.movieshub.app.presentation.rx.StickyAction
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.usecase.GetMovieListUseCase
import com.example.movieshub.utils.CustomException
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.lang.ref.WeakReference
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val movieListRouter: MovieListRouter,
    private val activityRef: WeakReference<Activity>
) {
    val progressVisibility = ObservableBoolean()
    val moviesList = ObservableArrayList<Result>()
    private val disposables = CompositeDisposable()
    var isLastPage = false
    private var TOTAL_PAGES = 0
    private var currentId = 1
    var currentPage = 1

    fun bound() {
        getMoviesList(currentId, 1)
    }

    private fun getMoviesList(currentId: Int, currentPage: Int) {
        progressVisibility.set(true)
        disposables.add(getMovieListUseCase.execute(currentId, currentPage)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe {
                handleMoviesListResponseResult(it)
            })

    }

    private fun handleMoviesListResponseResult(result: GetMovieListUseCase.Result) {
        progressVisibility.set(false)
        when(result) {
            is GetMovieListUseCase.Result.Success -> {
                if(!result.movie.results.isNullOrEmpty()) {
                    loadPage(result.movie)
                } else {
                    progressVisibility.set(false)
                }
            }
            is GetMovieListUseCase.Result.Failure -> {
                StickyAction<Boolean>().trigger(true)
                if (result.throwable is CustomException) {
                    if (moviesList.isEmpty()) {
                        result.throwable.showErrorMessage(activityRef.get()!!.baseContext)
                    } else {
                        CustomException(CustomException.ERROR_CODE.SOMETHING_WENT_WRONG)
                            .showErrorMessage(activityRef.get()!!.baseContext)
                    }
                } else if (result.throwable is IOException || result.throwable is HttpException) {
                    CustomException(CustomException.ERROR_CODE.SOMETHING_WENT_WRONG)
                        .showErrorMessage(activityRef.get()!!.baseContext)
                }
            }
        }
    }

    fun loadMoreData() {
        progressVisibility.set(true)
        currentPage++
        if (currentPage <= TOTAL_PAGES) {
            getMoviesList(currentId, currentPage)
        } else {
            currentId ++
            currentPage = 1
            isLastPage = true
            getMoviesList(currentId, currentPage)
        }
    }

    private fun loadPage(movie: Movie) {
        progressVisibility.set(false)
        currentId = movie.id
        TOTAL_PAGES = movie.total_pages
        moviesList.addAll(movie.results)
    }

    fun unbound() {
        disposables.clear()
    }

    fun onMovieClicked(movie: Result) {
        movieListRouter.navigate(
            MovieListRouter.Route.MOVIE_DETAIL, Bundle().apply {
            putSerializable(EXTRA_MOVIE_DETAILS, movie)
        })
    }
}