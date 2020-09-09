package com.example.movieshub.data.mapper

import com.example.movieshub.data.response.movie_details.ReviewResponse
import com.example.movieshub.data.response.movie_list.MovieResponse
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.model.Review
import javax.inject.Inject

class MovieMapper @Inject constructor() {

    fun mapMovieResponse(response: MovieResponse): Movie {
        return Movie(
            id = response.id,
            results = response.results.map { it ->
                Result(
                    id = it.id,
                    original_title = it.original_title,
                    adult = it.adult,
                    poster_path = it.poster_path,
                    vote_average = it.vote_average,
                    reviews = mutableListOf(),
                    original_language = it.original_language,
                    backdrop_path = it.backdrop_path,
                    overview = it.overview,
                    popularity = it.popularity,
                    release_date = it.release_date,
                    vote_count = it.vote_count
                )
            },
            total_pages = response.total_pages,
            page = response.page
        )
    }

    fun mapReviewResponse(movieId: Int, reviewResponse: ReviewResponse): List<Review> {
        return reviewResponse.results.map {
            Review(
                movieId = movieId,
                id = it.id,
                url = it.url,
                content = it.content,
                author = it.author
            )
        }
    }
}