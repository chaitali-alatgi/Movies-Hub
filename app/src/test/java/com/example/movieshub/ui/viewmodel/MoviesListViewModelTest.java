package com.example.movieshub.ui.viewmodel

import android.app.Activity
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.usecase.movie_list.GetMovieListUseCase
import com.example.movieshub.ui.movie_list.MovieListRouter
import com.example.movieshub.ui.movie_list.RxJavaTestHooksResetRule
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.given
import io.reactivex.Observable
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.ref.WeakReference

@RunWith(MockitoJUnitRunner::class)
class MoviesListViewModelTest {

    @get:Rule
    var rxJavaTestHooksResetRule =
        RxJavaTestHooksResetRule()

    @Mock lateinit var getMovieListUseCase: GetMovieListUseCase
    @Mock lateinit var movieListRouter: MovieListRouter
    @Mock lateinit var activity: WeakReference<Activity>
    private lateinit var viewModel: MoviesListViewModel

    @Before
    fun setUp() {
        viewModel = MoviesListViewModel(getMovieListUseCase, movieListRouter, activity)
    }

    @Test
    fun loadPage() {
        val movie = Movie(
            results = listOf(
                mock(Result::class.java),
                mock(Result::class.java)
            ),
            id = 1,
            total_pages = 1,
            page = 1
        )
        given(getMovieListUseCase.execute(any(), any()))
            .willReturn(Observable.just(GetMovieListUseCase.Result.Success(movie)))

        viewModel.bound()

        assertThat(viewModel.moviesList.size, equalTo(2))
    }

    @Test
    fun onMovieClicked() {
        val movie = mock(Result::class.java)
        viewModel.onMovieClicked(movie)
        verify(movieListRouter).navigate(eq(MovieListRouter.Route.MOVIE_DETAIL), any())
    }
}