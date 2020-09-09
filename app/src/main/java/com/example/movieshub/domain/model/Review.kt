package com.example.movieshub.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Review")
data class Review(
    @PrimaryKey
    var movieId: Int,
    var author: String,
    var content: String,
    var id: String,
    var url: String
)