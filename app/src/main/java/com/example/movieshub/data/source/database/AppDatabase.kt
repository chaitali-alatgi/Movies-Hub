package com.example.movieshub.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieshub.domain.model.Review
import com.example.movieshub.domain.model.movie_list.Movie
import com.example.movieshub.domain.model.movie_list.Result
import com.example.movieshub.utils.DB_NAME
import com.example.movieshub.utils.DB_VERSION

@Database(entities = [Movie::class, Review::class, Result::class],
    version = DB_VERSION, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object{
        fun getInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, DB_NAME
            ).build()
        }
    }
}