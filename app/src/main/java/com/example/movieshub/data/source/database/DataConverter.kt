package com.example.movieshub.data.source.database

import androidx.room.TypeConverter
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.domain.model.Review
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataConverter {

    @TypeConverter
    fun fromResultList(value: List<Result>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Result>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toResultList(value: String): List<Result> {
        val gson = Gson()
        val type = object : TypeToken<List<Result>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromReviewList(value: List<Review>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Review>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toReviewList(value: String): List<Review> {
        val gson = Gson()
        val type = object : TypeToken<List<Review>>() {}.type
        return gson.fromJson(value, type)
    }

}