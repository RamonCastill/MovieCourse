package com.rrstudio.movies.data.remote

import com.rrstudio.movies.aplication.AppConstants
import com.rrstudio.movies.data.model.MovieList
import com.rrstudio.movies.repository.WebService

class RemoteMovieDataSource(private val webService: WebService) {

    suspend fun getUpcomingMovies(): MovieList = webService.getUpcomingMovies(AppConstants.APY_KEY)

    suspend fun getTopRatedMovies(): MovieList = webService.getTopRatedMovies(AppConstants.APY_KEY)

    suspend fun getPopularMovies(): MovieList = webService.getPopularMovies(AppConstants.APY_KEY)

}