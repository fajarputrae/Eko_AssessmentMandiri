package com.example.pilottestmandirieko.repository


import com.example.pilottestmandirieko.model.*
import com.example.pilottestmandirieko.util.Constant
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("genre/movie/list")
    suspend fun allMovieGenre(@Query("api_key") apiKey: String): NetworkResponse<ListGenreModel, ResponseError>

    @GET("movie/{param}/lists")
    suspend fun allMovie(
        @Path("param") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): NetworkResponse<ListMovieModel, ResponseError>

    @GET("movie/{param}")
    suspend fun detailMovie(
        @Path("param") movieId: String,
        @Query("api_key") apiKey: String
    ): NetworkResponse<DetailMovieModel, ResponseError>

    @GET("movie/{param}/videos")
    suspend fun movieVideo(
        @Path("param") movieId: String,
        @Query("api_key") apiKey: String
    ): NetworkResponse<MovieVideoModel, ResponseError>

    @GET("movie/{param}/reviews")
    suspend fun movieReview(
        @Path("param") movieId: String,
        @Query("api_key") apiKey: String,
        @Query("page") page: String
    ): NetworkResponse<MovieReviewModel, ResponseError>

}

open class MovieRepository(private val movieService: MovieService) {

    suspend fun getAllMovieGenre(): NetworkResponse<ListGenreModel, ResponseError> {
        return movieService.allMovieGenre(apiKey = Constant.TMDB_API_KEY)
    }

    suspend fun getAllMovie(
        movieId: String,
        page: String
    ): NetworkResponse<ListMovieModel, ResponseError> {
        return movieService.allMovie(movieId = movieId, apiKey = Constant.TMDB_API_KEY, page = page)
    }

    suspend fun getDetailMovie(movieId: String): NetworkResponse<DetailMovieModel, ResponseError> {
        return movieService.detailMovie(movieId = movieId, apiKey = Constant.TMDB_API_KEY)
    }

    suspend fun getMovieVideo(movieId: String): NetworkResponse<MovieVideoModel, ResponseError> {
        return movieService.movieVideo(movieId = movieId, apiKey = Constant.TMDB_API_KEY)
    }

    suspend fun getMovieReview(
        movieId: String,
        page: String
    ): NetworkResponse<MovieReviewModel, ResponseError> {
        return movieService.movieReview(
            movieId = movieId,
            apiKey = Constant.TMDB_API_KEY,
            page = page
        )
    }

}