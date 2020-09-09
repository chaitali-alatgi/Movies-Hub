package com.example.movieshub.data.network

import com.example.movieshub.data.response.movie_details.ReviewResponse
import com.example.movieshub.data.response.movie_list.MovieResponse
import io.reactivex.Observable
import javax.inject.Inject

class NetworkApiRequest @Inject constructor(private val iNetworkEndpoint: INetworkEndpoint) {

    fun getMoviesListRequest(currentId: Int, pageNumber: Int): Observable<MovieResponse> {
        return iNetworkEndpoint.getMoviesList(currentId, pageNumber)
    }

    fun getReviewsRequest(movieId: Int): Observable<ReviewResponse>{
        return iNetworkEndpoint.getReviewsList(movieId)
    }

}