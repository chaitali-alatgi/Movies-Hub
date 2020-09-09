package com.example.movieshub.domain.model.movie_list

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.movieshub.domain.model.Review
import java.io.Serializable

@Entity(tableName = "Result")
data class Result(
    @PrimaryKey
    var id: Int,
    var original_title: String,
    var poster_path: String,
    var vote_average: Double,
    var reviews: MutableList<Review>,
    var adult: Boolean,
    var backdrop_path: String,
    var original_language: String,
    var overview: String,
    var popularity: Double,
    var release_date: String,
    var vote_count: Int
): Serializable