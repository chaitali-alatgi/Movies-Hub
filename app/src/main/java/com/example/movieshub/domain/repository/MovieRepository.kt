package com.example.movieshub.domain.repository

import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.usecase.movie_list.GetMovieListRequest
import io.reactivex.Observable

interface MovieRepository {
    suspend fun getMoviesList(request: GetMovieListRequest): Response<Movie>
    fun getReviewList(movieId: Int): Observable<List<Review>>
}