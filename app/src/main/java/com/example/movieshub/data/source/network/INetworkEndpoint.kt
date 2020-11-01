package com.example.movieshub.data.source.network

import com.example.movieshub.data.response.movie_details.ReviewResponse
import com.example.movieshub.data.response.movie_list.MovieResponse
import io.reactivex.Observable
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface INetworkEndpoint {

    @GET("4/list/{currentId}")
    fun getMoviesList(
        @Path("currentId") currentId: Int,
        @Query("pageNumber") pageNumber: Int): Deferred<MovieResponse>

    @GET("3/movie/{movieId}/reviews")
    fun getReviewsList(@Path("movieId") movieId: Int,
                       @Query("language") language: String = "en-US",
                       @Query("page") page: Int = 1): Observable<ReviewResponse>
}