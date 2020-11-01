package com.example.movieshub.data.source.database

import com.example.movieshub.ui.BaseApplication
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.utils.CustomException
import io.reactivex.Observable
import javax.inject.Inject

class DiskDataSource @Inject constructor(application: BaseApplication) {

    companion object {
        var database: AppDatabase? = null
    }

    init {
        database = AppDatabase.getInstance(application)
    }

    fun insertMovieToDatabase(movie: Movie) {
        database?.movieDao()?.addAllMovies(movie)
    }

    fun getMoviesFromDatabase(currentId: Int, pageNumber: Int): Movie? {
        return database?.movieDao()?.getAllMovies(currentId, pageNumber)
    }

    fun saveReviewsToRoomDatabase(reviewList: List<Review>) {
        database?.movieDao()?.addReviewList(reviewList)
    }

    fun getAllReviewsFromDatabase(movieId: Int): Observable<List<Review>> {
        return Observable.fromCallable {
            var savedReviews = database?.movieDao()?.getReviewList(movieId)
            if(savedReviews == null || savedReviews.isNullOrEmpty()) {
                throw CustomException(CustomException.ERROR_CODE.NO_DATA)
            }
            return@fromCallable savedReviews
        }
    }
}