package com.example.movieshub.data.response.movie_list

data class MovieResponse(
    var average_rating: Double,
    var backdrop_path: String,
    var description: String,
    var id: Int,
    var iso_3166_1: String,
    var iso_639_1: String,
    var name: String,
    var page: Int,
    var poster_path: String,
    var public: Boolean,
    var results: List<Result>,
    var revenue: Long,
    var runtime: Int,
    var sort_by: String,
    var total_pages: Int,
    var total_results: Int
)