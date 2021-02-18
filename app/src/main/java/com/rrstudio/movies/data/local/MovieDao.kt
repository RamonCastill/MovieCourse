package com.rrstudio.movies.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rrstudio.movies.data.model.MovieEntity


@Dao
interface MovieDao {

    @Query("SELECT * FROM MovieEntity")
    suspend fun getAllMovies(): List<MovieEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE) //onConflict es para no repetir elementos
    suspend fun saveMovie(movie: MovieEntity)

}