package com.example.movieshub.domain.usecase

import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetReviewsListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    sealed class Result {
        data class Success(val reviewList: List<Review>): Result()
        data class Failure(val throwable: Throwable): Result()
    }

    fun execute(movieId: Int): Observable<Result> {
        return movieRepository.getReviewList(movieId)
            .map {
                Result.Success(it) as Result
            }
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}