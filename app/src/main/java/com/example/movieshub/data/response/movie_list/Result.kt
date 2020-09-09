package com.example.movieshub.data.response.movie_list

data class Result(
    var adult: Boolean,
    var backdrop_path: String,
    var genre_ids: List<Int>,
    var id: Int,
    var media_type: String,
    var original_language: String,
    var original_title: String,
    var overview: String,
    var popularity: Double,
    var poster_path: String,
    var release_date: String,
    var title: String,
    var video: Boolean,
    var vote_average: Double,
    var vote_count: Int
)