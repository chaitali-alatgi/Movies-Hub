package com.example.movieshub.ui.viewmodel.model

import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.movie_list.Movie

sealed class MoviesListState {
    abstract val response: Response<Movie>?
}
data class SuccessMoviesListState(override val response: Response<Movie>) : MoviesListState()
data class LoadingMoviesListState(val isLoading: Boolean, override val response: Response<Movie>?) : MoviesListState()
data class ErrorMoviesListState(override val response: Response<Movie>) : MoviesListState()