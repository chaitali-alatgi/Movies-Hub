package com.example.movieshub.data.repository

import com.example.movieshub.data.source.database.AppDatabase
import com.example.movieshub.data.mapper.MovieMapper
import com.example.movieshub.data.source.database.DiskDataSource
import com.example.movieshub.data.source.network.NetworkDataSource
import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.repository.MovieRepository
import com.example.movieshub.domain.usecase.movie_list.GetMovieListRequest
import com.example.movieshub.utils.CustomException
import com.example.movieshub.utils.InternetUtil
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieMapper: MovieMapper,
    private val networkDataSource: NetworkDataSource,
    private val diskDataSource: DiskDataSource
): MovieRepository  {

    override suspend fun getMoviesList(request: GetMovieListRequest): Response<Movie> {
        if (InternetUtil.isNetworkAvailable()) {
            var movieResponse =
                networkDataSource.getMoviesListRequest(request.currentID, request.currentPage)
            if (movieResponse is Response.Success) {
                diskDataSource.insertMovieToDatabase(movieMapper.map(movieResponse.data))
                return Response.Success(movieMapper.map(movieResponse.data))
            }
        }
        return Response.Error(IOException())
    }

    override fun getReviewList(movieId: Int): Observable<List<Review>> {
            if(InternetUtil.isNetworkAvailable()) {
                var reviewResponse=
                    networkDataSource.getReviewsRequest(movieId)
                    .map {
                        movieMapper.mapReviewResponse(movieId, it)
                    }
             //   diskDataSource.saveReviewsToRoomDatabase(reviewResponse)
                return reviewResponse
            } else {
                return diskDataSource.getAllReviewsFromDatabase(movieId)
            }
        }
}