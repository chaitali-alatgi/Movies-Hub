package com.example.movieshub.data.database

import androidx.room.*
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie

@Dao
interface MovieDao {

    @Query("SELECT * FROM Movie where id =:currentId and page =:pageNumber")
    fun getAllMovies(currentId: Int, pageNumber: Int): Movie?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addAllMovies(movieList: Movie)

    @Query("SELECT * FROM Review where movieId =:movieId")
    fun getReviewList(movieId: Int): List<Review>?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addReviewList(reviewList: List<Review>)

}