package com.example.movieshub.domain.usecase.movie_list

import com.example.movieshub.domain.model.Response
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.repository.MovieRepository
import com.example.movieshub.domain.usecase.base.BaseUseCase
import javax.inject.Inject

open class GetMovieListUseCase @Inject constructor(val repository: MovieRepository): BaseUseCase<GetMovieListRequest, Movie>() {

    override suspend fun run(): Response<Movie> {
        return repository.getMoviesList(request!!)
    }
}