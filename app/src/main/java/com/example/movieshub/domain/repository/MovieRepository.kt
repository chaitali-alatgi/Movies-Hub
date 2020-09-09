package com.example.movieshub.domain.repository

import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import io.reactivex.Observable

interface MovieRepository {
    fun getMoviesList(currentId: Int, pageNumber: Int): Observable<Movie>
    fun getReviewList(movieId: Int): Observable<List<Review>>
}