package com.example.movieshub.data.repository

import com.example.movieshub.data.database.AppDatabase
import com.example.movieshub.data.mapper.MovieMapper
import com.example.movieshub.data.network.NetworkApiRequest
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.repository.MovieRepository
import com.example.movieshub.utils.CustomException
import com.example.movieshub.utils.InternetUtil
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val movieMapper: MovieMapper,
    private val networkApiRequest: NetworkApiRequest,
    private val appDatabase: AppDatabase
): MovieRepository  {

    override fun getMoviesList(currentId: Int, pageNumber: Int): Observable<Movie> {
        if(InternetUtil.isInternetOn()) {
            var movie=
                networkApiRequest.getMoviesListRequest(currentId, pageNumber)
                .map {
                    movieMapper.mapMovieResponse(it)
                }
            saveMovieToRoomDatabase(movie)
            return movie
        } else {
            return getMoviesFromDatabase(currentId, pageNumber)
        }
    }

    private fun saveMovieToRoomDatabase(response: Observable<Movie>) {
        response.subscribeOn(Schedulers.io()).subscribe(
            Consumer {
                appDatabase.movieDao().addAllMovies(it)
            }
        )
    }

    private fun getMoviesFromDatabase(currentId: Int, pageNumber: Int): Observable<Movie> {
        return Observable.fromCallable {
            var savedMovies = appDatabase.movieDao().getAllMovies(currentId, pageNumber)
            if(savedMovies == null || (savedMovies.results.isNullOrEmpty())) {
                throw CustomException(CustomException.ERROR_CODE.NO_DATA)
            }
            return@fromCallable savedMovies
        }
    }

    override fun getReviewList(movieId: Int): Observable<List<Review>> {
            if(InternetUtil.isInternetOn()) {
                var reviewResponse=
                    networkApiRequest.getReviewsRequest(movieId)
                    .map {
                        movieMapper.mapReviewResponse(movieId, it)
                    }
                saveReviewsToRoomDatabase(reviewResponse)
                return reviewResponse
            } else {
                return getAllReviewsFromDatabase(movieId)
            }
        }

        private fun saveReviewsToRoomDatabase(reviewResponse: Observable<List<Review>>) {
            reviewResponse.subscribeOn(Schedulers.io()).subscribe(
                Consumer {
                    appDatabase.movieDao().addReviewList(it)
                }
            )
        }

        private fun getAllReviewsFromDatabase(movieId: Int): Observable<List<Review>> {
            return Observable.fromCallable {
                var savedReviews = appDatabase.movieDao().getReviewList(movieId)
                if(savedReviews == null || savedReviews.isNullOrEmpty()) {
                    throw CustomException(CustomException.ERROR_CODE.NO_DATA)
                }
                return@fromCallable savedReviews
            }
        }
}