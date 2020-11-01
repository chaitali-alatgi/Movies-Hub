package com.example.movieshub.data.source.network

import android.app.Application
import com.example.movieshub.data.response.movie_details.ReviewResponse
import com.example.movieshub.data.response.movie_list.MovieResponse
import com.example.movieshub.domain.model.Response
import com.example.movieshub.utils.InternetUtil
import com.example.movieshub.utils.NetworkSystem
import io.reactivex.Observable
import java.io.IOException
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val iNetworkEndpoint: INetworkEndpoint
){
    suspend fun getMoviesListRequest(currentId: Int, pageNumber: Int): Response<MovieResponse> {
        var movieResponse = iNetworkEndpoint.getMoviesList(currentId, pageNumber).await()
        return Response.Success(movieResponse)
    }

    fun getReviewsRequest(movieId: Int): Observable<ReviewResponse>{
        return iNetworkEndpoint.getReviewsList(movieId)
    }

}