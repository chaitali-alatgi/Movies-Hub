package com.example.movieshub.domain.usecase

import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.repository.MovieRepository
import io.reactivex.Observable
import javax.inject.Inject

class GetMovieListUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {

    sealed class Result {
        data class Success(val movie: Movie): Result()
        data class Failure(val throwable: Throwable): Result()
    }

    fun execute(currentId: Int, pageNumber: Int): Observable<Result> {
        return movieRepository.getMoviesList(currentId, pageNumber)
            .map {
                Result.Success(it) as Result
            }
            .onErrorReturn {
                Result.Failure(it)
            }
    }
}