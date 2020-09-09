package com.example.movieshub.data.response.movie_details

data class ReviewResponse(
    var id: Int,
    var page: Int,
    var results: List<Result>,
    var total_pages: Int,
    var total_results: Int
)