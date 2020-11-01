package com.example.movieshub.ui.viewmodel

import android.os.Bundle
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieshub.ui.details.MovieDetailActivity.Companion.EXTRA_MOVIE_DETAILS
import com.example.movieshub.ui.movie_list.*
import com.example.movieshub.ui.viewmodel.model.ErrorMoviesListState
import com.example.movieshub.ui.viewmodel.model.LoadingMoviesListState
import com.example.movieshub.ui.viewmodel.model.MoviesListState
import com.example.movieshub.ui.viewmodel.model.SuccessMoviesListState
import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.usecase.movie_list.GetMovieListRequest
import com.example.movieshub.domain.usecase.movie_list.GetMovieListUseCase
import com.example.movieshub.utils.launchSilent
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.Job
import javax.inject.Inject

class MoviesListViewModel @Inject constructor(
    private val getMovieListUseCase: GetMovieListUseCase,
    private val movieListRouter: MovieListRouter
): ViewModel() {

    var moviesListStateLiveData = MutableLiveData<MoviesListState>()
    private var job: Job = Job()

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

    private fun getMoviesList(currentId: Int, currentPage: Int) = launchSilent(coroutineContext, job) {
        moviesListStateLiveData.postValue(
            LoadingMoviesListState(
                true,
                null
            )
        )
        val request = GetMovieListRequest(currentId, currentPage)
        val response = getMovieListUseCase.execute(request)
        handleMoviesListResponseResult(response)
    }

    private fun handleMoviesListResponseResult(response: Response<Movie>) {
        if (response is Response.Success) {
            moviesListStateLiveData.postValue(
                SuccessMoviesListState(
                    response
                )
            )
        } else if (response is Response.Error) {
            moviesListStateLiveData.postValue(
                ErrorMoviesListState(
                    response
                )
            )
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

    fun loadPage(movie: Movie) {
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