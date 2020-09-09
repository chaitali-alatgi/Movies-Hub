package com.example.movieshub.domain.model.movie_list

import androidx.room.*
import java.io.Serializable

@Entity(tableName = "Movie")
data class Movie(
    @PrimaryKey
    var id: Int,
    var results: List<Result>,
    var page: Int,
    var total_pages: Int
): Serializable